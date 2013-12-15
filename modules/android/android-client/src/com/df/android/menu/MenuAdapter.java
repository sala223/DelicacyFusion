package com.df.android.menu;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Store;
import com.df.android.utils.CacheMgr;

public class MenuAdapter extends BaseAdapter {
	// private Store store;
	private Context context;
	private List<Item> items = new ArrayList<Item>();
	private ItemCategory itemCategory;

	public MenuAdapter(Context context, Store store, ItemCategory category) {
		super();
		this.context = context;
		this.itemCategory = category;

		for (Item item : store.getMenu().getItems()) {
			if ("SYS00000".equals(category.getCode())
					|| item.getCategories().contains(category.getCode())) {
				items.add(item);
			}
		}
	}

	public ItemCategory getMenuItemType() {
		return itemCategory;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Item getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = convertView;
		if (view == null)
			view = inflator.inflate(R.layout.menuitem, null);

		final Item item = items.get(position);

		final TextView tvName = (TextView) view.findViewById(R.id.menuItemName);
		final ImageView ivImage = (ImageView) view
				.findViewById(R.id.menuItemImage);
		final TextView tvPrevPrice = (TextView) view
				.findViewById(R.id.menuItemPrevPrice);
		final TextView tvPrice = (TextView) view
				.findViewById(R.id.menuItemPrice);
		view.findViewById(R.id.btnMenuItemOrder).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						MenuEventDispatcher.sendEvent(new ItemOrderEvent(item));
					}

				});

		tvName.setText(item.getName());
		tvPrevPrice.setText("" + item.getPrice());
		tvPrevPrice.setPaintFlags(tvPrevPrice.getPaintFlags()
				| Paint.STRIKE_THRU_TEXT_FLAG);
		tvPrevPrice.setVisibility(View.VISIBLE);
		tvPrice.setText("" + item.getPrice());

		String imageFile = item.getImage();
		if (imageFile != null && !"".equals(imageFile)) {
			String tmp = CacheMgr.instance().getCacheDir(
					GlobalSettings.instance().getCurrentTenantCode(),
					GlobalSettings.instance().getCurrentStoreCode())
					+ "/image/" + imageFile;
			Log.d(getClass().getName(), "image of " + item.getCode() + ": "
					+ tmp);

			ivImage.setImageBitmap(BitmapFactory.decodeFile(tmp));
		} else
			ivImage.setImageResource(R.drawable.no_pic);

		ivImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				showMenuItemDetail(view, item);
			}

		});

		// ivImage.setOnLongClickListener(new OnLongClickListener() {
		// @Override
		// public boolean onLongClick(View view) {
		// DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
		// view);
		// view.setTag(item);
		// view.startDrag(null, shadowBuilder, view, 0);
		// return false;
		// }
		// });

		return view;
	}

	private Animator mCurrentAnimator;
	private int mShortAnimationDuration = 200;

	private void showMenuItemDetail(final View srcView, final Item item) {
		// If there's an animation in progress, cancel it
		// immediately and proceed with this one.
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		final Activity activity = (Activity) context;
		((TextView) activity.findViewById(R.id.tvMenuItemDetailName))
				.setText(item.getName());
		((TextView) activity.findViewById(R.id.tvMenuItemDetailPrice))
				.setText("" + item.getPrice());
		final View expandedView = activity.findViewById(R.id.menuItemDetail);
		final ImageView expandedImageView = (ImageView) activity
				.findViewById(R.id.enlargedImage);

		String imageFile = item.getImage();
		if (imageFile != null && !"".equals(imageFile)) {
			try {
				String tmp = CacheMgr.instance().getCacheDir(
						GlobalSettings.instance().getCurrentTenantCode(),
						GlobalSettings.instance().getCurrentStoreCode())
						+ "/image/" + imageFile;
				expandedImageView.setImageBitmap(BitmapFactory.decodeFile(tmp));
			} catch (Exception e) {
				Log.e(getClass().getName(), "Fail to load image file '"
						+ imageFile + "' due to " + e);
			}
		} else
			expandedImageView.setImageResource(R.drawable.no_pic);

		activity.findViewById(R.id.btnMenuItemDetailOrder).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View view) {
						MenuEventDispatcher.sendEvent(new ItemOrderEvent(item));
					}
				});

		// Calculate the starting and ending bounds for the zoomed-in image.
		// This step involves lots of math. Yay, math.
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		// The start bounds are the global visible rectangle of the thumbnail,
		// and the final bounds are the global visible rectangle of the
		// container
		// view. Also set the container view's offset as the origin for the
		// bounds, since that's the origin for the positioning animation
		// properties (X, Y).
		srcView.getGlobalVisibleRect(startBounds);
		activity.findViewById(R.id.menuContainer).getGlobalVisibleRect(
				finalBounds, globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		// Adjust the start bounds to be the same aspect ratio as the final
		// bounds using the "center crop" technique. This prevents undesirable
		// stretching during the animation. Also calculate the start scaling
		// factor (the end scaling factor is always 1.0).
		float startScale;
		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
				.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		// Hide the thumbnail and show the zoomed-in view. When the animation
		// begins, it will position the zoomed-in view in the place of the
		// thumbnail.
		srcView.setAlpha(0f);
		expandedView.setVisibility(View.VISIBLE);

		// Set the pivot point for SCALE_X and SCALE_Y transformations
		// to the top-left corner of the zoomed-in view (the default
		// is the center of the view).
		expandedView.setPivotX(0f);
		expandedView.setPivotY(0f);

		// Construct and run the parallel animation of the four translation and
		// scale properties (X, Y, SCALE_X, and SCALE_Y).
		AnimatorSet set = new AnimatorSet();
		set.play(
				ObjectAnimator.ofFloat(expandedView, View.X, startBounds.left,
						finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedView, View.Y,
						startBounds.top, finalBounds.top))
				.with(ObjectAnimator.ofFloat(expandedView, View.SCALE_X,
						startScale, 1f))
				.with(ObjectAnimator.ofFloat(expandedView, View.SCALE_Y,
						startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// Upon clicking the zoomed-in image, it should zoom back down
		// to the original bounds and show the thumbnail instead of
		// the expanded image.
		final float startScaleFinal = startScale;
		expandedView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				// Animate the four positioning/sizing properties in parallel,
				// back to their original values.
				AnimatorSet set = new AnimatorSet();
				set.play(
						ObjectAnimator.ofFloat(expandedView, View.X,
								startBounds.left))
						.with(ObjectAnimator.ofFloat(expandedView, View.Y,
								startBounds.top))
						.with(ObjectAnimator.ofFloat(expandedView,
								View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator.ofFloat(expandedView,
								View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						srcView.setAlpha(1f);
						expandedView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						srcView.setAlpha(1f);
						expandedView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}
}