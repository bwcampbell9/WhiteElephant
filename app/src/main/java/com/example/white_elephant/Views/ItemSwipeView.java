package com.example.white_elephant.Views;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.white_elephant.Models.ItemModel;
import com.example.white_elephant.R;

public class ItemSwipeView extends Fragment {

    int width, height;
    double xCenter, yCenter;
    int x_cord, y_cord, x, y;
    static final double MAX_ROTATION = .2;

    enum Status {
        NOTHING,
        LIKE,
        DISLIKE,
        SAVE,
    }
    Status status = Status.NOTHING;

    /**
     * Create a new instance of ItemSwipeView, initialized to
     * show the view of the provided itemModel.
     */
    public static ItemSwipeView newInstance(ItemModel model) {
        ItemSwipeView f = new ItemSwipeView();

        // Supply model input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("model", model);
        f.setArguments(args);

        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.item_swipe_layout, container, false);
        view.findViewById(R.id.imageView).setClipToOutline(true);
        TextView textView = (TextView) view.findViewById(R.id.titleText);
        TextView descriptionView = (TextView) view.findViewById(R.id.descriptionText);

        final ImageView likeView = view.findViewById(R.id.likeIcon);
        final ImageView dislikeView = view.findViewById(R.id.dislikeIcon);
        final ImageView saveView = view.findViewById(R.id.saveIcon);

        likeView.setAlpha(0f);
        dislikeView.setAlpha(0f);
        saveView.setAlpha(0f);

        ItemModel model = getModel();

        textView.setText(model.getName() + ", $" + model.getValue());
        descriptionView.setText(model.getDescription());

        view.setX(0);
        view.setY(0);
        view.setRotation(0);
        likeView.setAlpha(0f);
        dislikeView.setAlpha(0f);
        saveView.setAlpha(0f);

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                width = container.getWidth();
                height = container.getHeight();
                xCenter = width / 2;
                yCenter = height / 2;

                // TODO: handle possible crash here if this fragment is used outside of MainSwipeView
                MainSwipeView parentFragment = (MainSwipeView) getParentFragment();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        x_cord = (int) event.getRawX() - x;
                        // smoother animation.
                        y_cord = (int) event.getRawY() - y;

                        view.setX(x_cord);
                        view.setY(y_cord);
                        view.setRotation((float) ((x_cord / xCenter) * (90) * MAX_ROTATION));
                        float xVal = (float)((x_cord) / (xCenter / 2.0));
                        float yVal = (float)((y_cord) / (yCenter / 2.0));
                        if(xVal < .5 && xVal > -.5) {
                            saveView.setAlpha(-yVal);
                        } else {
                            saveView.setAlpha(0f);
                        }
                        likeView.setAlpha(xVal);
                        dislikeView.setAlpha(-xVal);

                        if (x_cord > (xCenter / 2)) {
                            status = Status.LIKE;
                        } else if (x_cord < -(xCenter / 2.0)) {
                            status = Status.DISLIKE;
                        } else if (y_cord < -(yCenter / 2.0)) {
                            status = Status.SAVE;
                        } else {
                            status = Status.NOTHING;
                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        x_cord = (int) event.getRawX() - x;
                        y_cord = (int) event.getRawY() - y;

                        //tvUnLike.setAlpha(0);
                        //tvLike.setAlpha(0);

                        switch (status) {
                            case LIKE:
                                parentFragment.likeItem();
                                break;
                            case DISLIKE:
                                parentFragment.dislikeItem();
                                break;
                            case SAVE:
                                parentFragment.saveItem();
                                break;
                            case NOTHING:
                                view.setX(0);
                                view.setY(0);
                                view.setRotation(0);
                                likeView.setAlpha(0f);
                                dislikeView.setAlpha(0f);
                                saveView.setAlpha(0f);
                                break;
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        return view;
    }


    public ItemModel getModel() {
        return getArguments().getParcelable("model");
    }
}
