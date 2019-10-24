package com.zoovu.zuuvochat.fragments.chat_room


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.LayoutAnimationController
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zoovu.zuuvochat.MainActivity

import com.zoovu.zuuvochat.R
import com.zoovu.zuuvochat.domain.Model
import com.zoovu.zuuvochat.domain.Type
import com.zoovu.zuuvochat.domain.viewmodels.ConversationViewModel
import com.zoovu.zuuvochat.fragments.chat_room.chat_room_list.ChatRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_chat_room.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 *
 */
class ChatRoomFragment :ChatRoomController() {

    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewAdapter:ChatRecyclerViewAdapter
    private lateinit var conViewModel:ConversationViewModel
    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.run {
            conViewModel = ViewModelProviders.of(this).get(ConversationViewModel::class.java)
        }?: throw Exception("Invalid activity.")
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
    }

    private fun dpToPixelsInt(dp:Int):Int {
        if (context != null) {
            return (dp *resources.displayMetrics.density + 0.5f).toInt()
        }
        return 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager  = LinearLayoutManager(activity as MainActivity)
        viewAdapter = ChatRecyclerViewAdapter(conViewModel, this, activity as MainActivity)
        viewManager.stackFromEnd = true
        viewManager.reverseLayout = true

        view.viewTreeObserver.addOnGlobalLayoutListener {
            var heightDiff = view.rootView.height - view.height
            if (heightDiff > dpToPixelsInt(200)) {
                chat_recycler_view.smoothScrollToPosition(0)
            }
        }

        (chat_recycler_view as RecyclerView) .apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        conViewModel.selectedConversation.observe( this, Observer {
            chat_recycler_view.smoothScrollToPosition(0)
        })

        message_send_button.onClick {
            if (message_text_input.text.toString().isNotEmpty()) {
                conViewModel.sendReply(Model.Message("none", message_text_input.text.toString(), Type.USER_TEXT))
                message_text_input.text.clear()
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun sendReply(message: Model.Message) {
        conViewModel.sendReply(message)
    }

    override fun zoomImageFromThumb(thumbView: View, imgUrl:String) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        thumbView.hideKeyboard()
        // Load the high-resolution "zoomed-in" image.
        val expandedImageView: ImageView = high_res_image_view
        Glide
            .with(this)
            .asBitmap()
            .load(imgUrl)
            .fitCenter()
            .into(expandedImageView)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        container
            .getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                expandedImageView,
                View.X,
                startBounds.left,
                finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

}
