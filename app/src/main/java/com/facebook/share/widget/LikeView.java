package com.facebook.share.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.p001v4.app.Fragment;
import android.support.p001v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.C0750R;
import com.facebook.FacebookException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.internal.LikeBoxCountView;
import com.facebook.share.internal.LikeButton;

public class LikeView extends FrameLayout {
    private static final int NO_FOREGROUND_COLOR = -1;
    private AuxiliaryViewPosition auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout containerView;
    /* access modifiers changed from: private */
    public LikeActionControllerCreationCallback creationCallback;
    private int edgePadding;
    private boolean explicitlyDisabled;
    private int foregroundColor = -1;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.DEFAULT;
    private int internalPadding;
    private LikeActionController likeActionController;
    private LikeBoxCountView likeBoxCountView;
    private LikeButton likeButton;
    private Style likeViewStyle = Style.DEFAULT;
    /* access modifiers changed from: private */
    public String objectId;
    /* access modifiers changed from: private */
    public ObjectType objectType;
    /* access modifiers changed from: private */
    public OnErrorListener onErrorListener;
    private FragmentWrapper parentFragment;
    private TextView socialSentenceView;

    public enum AuxiliaryViewPosition {
        BOTTOM("bottom", 0),
        INLINE("inline", 1),
        TOP("top", 2);
        
        static AuxiliaryViewPosition DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = BOTTOM;
        }

        static AuxiliaryViewPosition fromInt(int i) {
            for (AuxiliaryViewPosition auxiliaryViewPosition : values()) {
                if (auxiliaryViewPosition.getValue() == i) {
                    return auxiliaryViewPosition;
                }
            }
            return null;
        }

        private AuxiliaryViewPosition(String str, int i) {
            this.stringValue = str;
            this.intValue = i;
        }

        public String toString() {
            return this.stringValue;
        }

        /* access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }
    }

    public enum HorizontalAlignment {
        CENTER("center", 0),
        LEFT("left", 1),
        RIGHT("right", 2);
        
        static HorizontalAlignment DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = CENTER;
        }

        static HorizontalAlignment fromInt(int i) {
            for (HorizontalAlignment horizontalAlignment : values()) {
                if (horizontalAlignment.getValue() == i) {
                    return horizontalAlignment;
                }
            }
            return null;
        }

        private HorizontalAlignment(String str, int i) {
            this.stringValue = str;
            this.intValue = i;
        }

        public String toString() {
            return this.stringValue;
        }

        /* access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }
    }

    private class LikeActionControllerCreationCallback implements LikeActionController.CreationCallback {
        private boolean isCancelled;

        private LikeActionControllerCreationCallback() {
        }

        public void cancel() {
            this.isCancelled = true;
        }

        public void onComplete(LikeActionController likeActionController, FacebookException facebookException) {
            if (!this.isCancelled) {
                if (likeActionController != null) {
                    if (!likeActionController.shouldEnableView()) {
                        facebookException = new FacebookException("Cannot use LikeView. The device may not be supported.");
                    }
                    LikeView.this.associateWithLikeActionController(likeActionController);
                    LikeView.this.updateLikeStateAndLayout();
                }
                if (!(facebookException == null || LikeView.this.onErrorListener == null)) {
                    LikeView.this.onErrorListener.onError(facebookException);
                }
                LikeActionControllerCreationCallback unused = LikeView.this.creationCallback = null;
            }
        }
    }

    private class LikeControllerBroadcastReceiver extends BroadcastReceiver {
        private LikeControllerBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            boolean z = true;
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String string = extras.getString(LikeActionController.ACTION_OBJECT_ID_KEY);
                if (!Utility.isNullOrEmpty(string) && !Utility.areObjectsEqual(LikeView.this.objectId, string)) {
                    z = false;
                }
            }
            if (z) {
                if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED.equals(action)) {
                    LikeView.this.updateLikeStateAndLayout();
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR.equals(action)) {
                    if (LikeView.this.onErrorListener != null) {
                        LikeView.this.onErrorListener.onError(NativeProtocol.getExceptionFromErrorData(extras));
                    }
                } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET.equals(action)) {
                    LikeView.this.setObjectIdAndTypeForced(LikeView.this.objectId, LikeView.this.objectType);
                    LikeView.this.updateLikeStateAndLayout();
                }
            }
        }
    }

    public enum ObjectType {
        UNKNOWN("unknown", 0),
        OPEN_GRAPH(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_OPENGRAPH, 1),
        PAGE("page", 2);
        
        public static ObjectType DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = UNKNOWN;
        }

        public static ObjectType fromInt(int i) {
            for (ObjectType objectType : values()) {
                if (objectType.getValue() == i) {
                    return objectType;
                }
            }
            return null;
        }

        private ObjectType(String str, int i) {
            this.stringValue = str;
            this.intValue = i;
        }

        public String toString() {
            return this.stringValue;
        }

        public int getValue() {
            return this.intValue;
        }
    }

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    public enum Style {
        STANDARD("standard", 0),
        BUTTON("button", 1),
        BOX_COUNT("box_count", 2);
        
        static Style DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = STANDARD;
        }

        static Style fromInt(int i) {
            for (Style style : values()) {
                if (style.getValue() == i) {
                    return style;
                }
            }
            return null;
        }

        private Style(String str, int i) {
            this.stringValue = str;
            this.intValue = i;
        }

        public String toString() {
            return this.stringValue;
        }

        /* access modifiers changed from: private */
        public int getValue() {
            return this.intValue;
        }
    }

    public LikeView(Context context) {
        super(context);
        initialize(context);
    }

    public LikeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        parseAttributes(attributeSet);
        initialize(context);
    }

    public void setObjectIdAndType(String str, ObjectType objectType2) {
        String coerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(str, null);
        if (objectType2 == null) {
            objectType2 = ObjectType.DEFAULT;
        }
        if (!Utility.areObjectsEqual(coerceValueIfNullOrEmpty, this.objectId) || objectType2 != this.objectType) {
            setObjectIdAndTypeForced(coerceValueIfNullOrEmpty, objectType2);
            updateLikeStateAndLayout();
        }
    }

    public void setLikeViewStyle(Style style) {
        if (style == null) {
            style = Style.DEFAULT;
        }
        if (this.likeViewStyle != style) {
            this.likeViewStyle = style;
            updateLayout();
        }
    }

    public void setAuxiliaryViewPosition(AuxiliaryViewPosition auxiliaryViewPosition2) {
        if (auxiliaryViewPosition2 == null) {
            auxiliaryViewPosition2 = AuxiliaryViewPosition.DEFAULT;
        }
        if (this.auxiliaryViewPosition != auxiliaryViewPosition2) {
            this.auxiliaryViewPosition = auxiliaryViewPosition2;
            updateLayout();
        }
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment2) {
        if (horizontalAlignment2 == null) {
            horizontalAlignment2 = HorizontalAlignment.DEFAULT;
        }
        if (this.horizontalAlignment != horizontalAlignment2) {
            this.horizontalAlignment = horizontalAlignment2;
            updateLayout();
        }
    }

    public void setForegroundColor(int i) {
        if (this.foregroundColor != i) {
            this.socialSentenceView.setTextColor(i);
        }
    }

    public void setOnErrorListener(OnErrorListener onErrorListener2) {
        this.onErrorListener = onErrorListener2;
    }

    public OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    public void setFragment(Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public void setFragment(android.app.Fragment fragment) {
        this.parentFragment = new FragmentWrapper(fragment);
    }

    public void setEnabled(boolean z) {
        this.explicitlyDisabled = !z;
        updateLikeStateAndLayout();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        setObjectIdAndType(null, ObjectType.UNKNOWN);
        super.onDetachedFromWindow();
    }

    private void parseAttributes(AttributeSet attributeSet) {
        if (attributeSet != null && getContext() != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C0750R.styleable.com_facebook_like_view);
            if (obtainStyledAttributes != null) {
                this.objectId = Utility.coerceValueIfNullOrEmpty(obtainStyledAttributes.getString(C0750R.styleable.com_facebook_like_view_com_facebook_object_id), null);
                this.objectType = ObjectType.fromInt(obtainStyledAttributes.getInt(C0750R.styleable.com_facebook_like_view_com_facebook_object_type, ObjectType.DEFAULT.getValue()));
                this.likeViewStyle = Style.fromInt(obtainStyledAttributes.getInt(C0750R.styleable.com_facebook_like_view_com_facebook_style, Style.DEFAULT.getValue()));
                if (this.likeViewStyle == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'style'");
                }
                this.auxiliaryViewPosition = AuxiliaryViewPosition.fromInt(obtainStyledAttributes.getInt(C0750R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position, AuxiliaryViewPosition.DEFAULT.getValue()));
                if (this.auxiliaryViewPosition == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'auxiliary_view_position'");
                }
                this.horizontalAlignment = HorizontalAlignment.fromInt(obtainStyledAttributes.getInt(C0750R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment, HorizontalAlignment.DEFAULT.getValue()));
                if (this.horizontalAlignment == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'horizontal_alignment'");
                }
                this.foregroundColor = obtainStyledAttributes.getColor(C0750R.styleable.com_facebook_like_view_com_facebook_foreground_color, -1);
                obtainStyledAttributes.recycle();
            }
        }
    }

    private void initialize(Context context) {
        this.edgePadding = getResources().getDimensionPixelSize(C0750R.dimen.com_facebook_likeview_edge_padding);
        this.internalPadding = getResources().getDimensionPixelSize(C0750R.dimen.com_facebook_likeview_internal_padding);
        if (this.foregroundColor == -1) {
            this.foregroundColor = getResources().getColor(C0750R.color.com_facebook_likeview_text_color);
        }
        setBackgroundColor(0);
        this.containerView = new LinearLayout(context);
        this.containerView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        initializeLikeButton(context);
        initializeSocialSentenceView(context);
        initializeLikeCountView(context);
        this.containerView.addView(this.likeButton);
        this.containerView.addView(this.socialSentenceView);
        this.containerView.addView(this.likeBoxCountView);
        addView(this.containerView);
        setObjectIdAndTypeForced(this.objectId, this.objectType);
        updateLikeStateAndLayout();
    }

    private void initializeLikeButton(Context context) {
        this.likeButton = new LikeButton(context, this.likeActionController != null && this.likeActionController.isObjectLiked());
        this.likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LikeView.this.toggleLike();
            }
        });
        this.likeButton.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    private void initializeSocialSentenceView(Context context) {
        this.socialSentenceView = new TextView(context);
        this.socialSentenceView.setTextSize(0, getResources().getDimension(C0750R.dimen.com_facebook_likeview_text_size));
        this.socialSentenceView.setMaxLines(2);
        this.socialSentenceView.setTextColor(this.foregroundColor);
        this.socialSentenceView.setGravity(17);
        this.socialSentenceView.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }

    private void initializeLikeCountView(Context context) {
        this.likeBoxCountView = new LikeBoxCountView(context);
        this.likeBoxCountView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    /* access modifiers changed from: private */
    public void toggleLike() {
        if (this.likeActionController != null) {
            Activity activity = null;
            if (this.parentFragment == null) {
                activity = getActivity();
            }
            this.likeActionController.toggleLike(activity, this.parentFragment, getAnalyticsParameters());
        }
    }

    private Activity getActivity() {
        Context context = getContext();
        while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    private Bundle getAnalyticsParameters() {
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, this.likeViewStyle.toString());
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_AUXILIARY_POSITION, this.auxiliaryViewPosition.toString());
        bundle.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_HORIZONTAL_ALIGNMENT, this.horizontalAlignment.toString());
        bundle.putString("object_id", Utility.coerceValueIfNullOrEmpty(this.objectId, ""));
        bundle.putString("object_type", this.objectType.toString());
        return bundle;
    }

    /* access modifiers changed from: private */
    public void setObjectIdAndTypeForced(String str, ObjectType objectType2) {
        tearDownObjectAssociations();
        this.objectId = str;
        this.objectType = objectType2;
        if (!Utility.isNullOrEmpty(str)) {
            this.creationCallback = new LikeActionControllerCreationCallback();
            if (!isInEditMode()) {
                LikeActionController.getControllerForObjectId(str, objectType2, this.creationCallback);
            }
        }
    }

    /* access modifiers changed from: private */
    public void associateWithLikeActionController(LikeActionController likeActionController2) {
        this.likeActionController = likeActionController2;
        this.broadcastReceiver = new LikeControllerBroadcastReceiver();
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR);
        intentFilter.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        instance.registerReceiver(this.broadcastReceiver, intentFilter);
    }

    private void tearDownObjectAssociations() {
        if (this.broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.broadcastReceiver);
            this.broadcastReceiver = null;
        }
        if (this.creationCallback != null) {
            this.creationCallback.cancel();
            this.creationCallback = null;
        }
        this.likeActionController = null;
    }

    /* access modifiers changed from: private */
    public void updateLikeStateAndLayout() {
        boolean z = !this.explicitlyDisabled;
        if (this.likeActionController == null) {
            this.likeButton.setSelected(false);
            this.socialSentenceView.setText(null);
            this.likeBoxCountView.setText(null);
        } else {
            this.likeButton.setSelected(this.likeActionController.isObjectLiked());
            this.socialSentenceView.setText(this.likeActionController.getSocialSentence());
            this.likeBoxCountView.setText(this.likeActionController.getLikeCountString());
            z &= this.likeActionController.shouldEnableView();
        }
        super.setEnabled(z);
        this.likeButton.setEnabled(z);
        updateLayout();
    }

    private void updateLayout() {
        int i;
        View view;
        int i2 = 1;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.containerView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.likeButton.getLayoutParams();
        if (this.horizontalAlignment == HorizontalAlignment.LEFT) {
            i = 3;
        } else {
            i = this.horizontalAlignment == HorizontalAlignment.CENTER ? 1 : 5;
        }
        layoutParams.gravity = i | 48;
        layoutParams2.gravity = i;
        this.socialSentenceView.setVisibility(8);
        this.likeBoxCountView.setVisibility(8);
        if (this.likeViewStyle == Style.STANDARD && this.likeActionController != null && !Utility.isNullOrEmpty(this.likeActionController.getSocialSentence())) {
            view = this.socialSentenceView;
        } else if (this.likeViewStyle == Style.BOX_COUNT && this.likeActionController != null && !Utility.isNullOrEmpty(this.likeActionController.getLikeCountString())) {
            updateBoxCountCaretPosition();
            view = this.likeBoxCountView;
        } else {
            return;
        }
        view.setVisibility(0);
        ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = i;
        LinearLayout linearLayout = this.containerView;
        if (this.auxiliaryViewPosition == AuxiliaryViewPosition.INLINE) {
            i2 = 0;
        }
        linearLayout.setOrientation(i2);
        if (this.auxiliaryViewPosition == AuxiliaryViewPosition.TOP || (this.auxiliaryViewPosition == AuxiliaryViewPosition.INLINE && this.horizontalAlignment == HorizontalAlignment.RIGHT)) {
            this.containerView.removeView(this.likeButton);
            this.containerView.addView(this.likeButton);
        } else {
            this.containerView.removeView(view);
            this.containerView.addView(view);
        }
        switch (this.auxiliaryViewPosition) {
            case TOP:
                view.setPadding(this.edgePadding, this.edgePadding, this.edgePadding, this.internalPadding);
                return;
            case BOTTOM:
                view.setPadding(this.edgePadding, this.internalPadding, this.edgePadding, this.edgePadding);
                return;
            case INLINE:
                if (this.horizontalAlignment == HorizontalAlignment.RIGHT) {
                    view.setPadding(this.edgePadding, this.edgePadding, this.internalPadding, this.edgePadding);
                    return;
                } else {
                    view.setPadding(this.internalPadding, this.edgePadding, this.edgePadding, this.edgePadding);
                    return;
                }
            default:
                return;
        }
    }

    private void updateBoxCountCaretPosition() {
        switch (this.auxiliaryViewPosition) {
            case TOP:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountView.LikeBoxCountViewCaretPosition.BOTTOM);
                return;
            case BOTTOM:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountView.LikeBoxCountViewCaretPosition.TOP);
                return;
            case INLINE:
                this.likeBoxCountView.setCaretPosition(this.horizontalAlignment == HorizontalAlignment.RIGHT ? LikeBoxCountView.LikeBoxCountViewCaretPosition.RIGHT : LikeBoxCountView.LikeBoxCountViewCaretPosition.LEFT);
                return;
            default:
                return;
        }
    }
}
