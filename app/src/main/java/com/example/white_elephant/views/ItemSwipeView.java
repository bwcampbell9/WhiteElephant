package com.example.white_elephant.views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.white_elephant.MainSwipeFragment;
import com.example.white_elephant.R;
import com.example.white_elephant.models.Item;
import com.example.white_elephant.util.Storage;

public class ItemSwipeView extends Fragment {

    int width;
    int height;
    double xCenter;
    double yCenter;
    int xCord;
    int yCord;
    int x;
    int y;
    static final double MAX_ROTATION = .2;
    private ImageView imageView;
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    enum Status {
        NOTHING,
        LIKE,
        DISLIKE,
        SAVE,
    }

    private Status status = Status.NOTHING;

    /**
     * Create a new instance of ItemSwipeView, initialized to
     * show the view of the provided itemModel.
     */
    public static ItemSwipeView newInstance(Item model) {
        ItemSwipeView f = new ItemSwipeView();

        // Supply model input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("model", model);
        f.setArguments(args);

        return f;
    }

    public void loadImageFromWebOperations(Drawable d) {
        imageView.setImageDrawable(d);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.item_swipe_layout, container, false);
        view.findViewById(R.id.imageView).setClipToOutline(true);
        TextView textView = (TextView) view.findViewById(R.id.titleText);
        TextView descriptionView = (TextView) view.findViewById(R.id.descriptionText);

        imageView = view.findViewById(R.id.imageView);
        final ImageView likeView = view.findViewById(R.id.likeIcon);
        final ImageView dislikeView = view.findViewById(R.id.dislikeIcon);
        final ImageView saveView = view.findViewById(R.id.saveIcon);


        item = getModel();

        textView.setText(item.getName() + ", $" + item.getValue());
        descriptionView.setText(item.getDescription());

        view.setX(0);
        view.setY(0);
        view.setRotation(0);
        likeView.setAlpha(0f);
        dislikeView.setAlpha(0f);
        saveView.setAlpha(0f);

        view.setOnTouchListener(
            (View v, MotionEvent event) -> {

                width = container.getWidth();
                height = container.getHeight();
                xCenter = width / 2.0;
                yCenter = height / 2.0;

                MainSwipeFragment parentFragment = (MainSwipeFragment) getParentFragment();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getRawX();
                        y = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        xCord = (int) event.getRawX() - x;
                        // smoother animation.
                        yCord = (int) event.getRawY() - y;

                        view.setX(xCord);
                        view.setY(yCord);
                        view.setRotation((float) ((xCord / xCenter) * (90) * MAX_ROTATION));
                        float xVal = (float)((xCord) / (xCenter / 2.0));
                        float yVal = (float)((yCord) / (yCenter / 2.0));
                        saveView.setAlpha(0f);
                        if(xVal < .5 && xVal > -.5) {
                            saveView.setAlpha(-yVal);
                        }
                        likeView.setAlpha(xVal);
                        dislikeView.setAlpha(-xVal);

                        if (xCord > (xCenter / 2)) {
                            status = Status.LIKE;
                        } else if (xCord < -(xCenter / 2.0)) {
                            status = Status.DISLIKE;
                        } else if (yCord < -(yCenter / 2.0)) {
                            status = Status.SAVE;
                        } else {
                            status = Status.NOTHING;
                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        xCord = (int) event.getRawX() - x;
                        yCord = (int) event.getRawY() - y;

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
        );

        Storage.getInstance().getImage(item.getImageUrl(), imageView, -1);
        return view;
    }


    public Item getModel() {
        return getArguments().getParcelable("model");
    }
}
