package com.moying.energyring.waylenBaseView.viewpagercards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.moying.energyring.R;


public class CardFragment extends Fragment {


    public static CardFragment newInstance(String stringtype, String workType) {
        CardFragment newFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("stringtype", stringtype);
        bundle.putString("workType", workType);
        newFragment.setArguments(bundle);
        //bundle可以在每个标签里传送数据
        return newFragment;
    }

    private CardView mCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adapter, container, false);
        mCardView = (CardView) view.findViewById(R.id.cardView);
        mCardView.setMaxCardElevation(mCardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);
        Button TypeBtn = (Button) getActivity().findViewById(R.id.cardTypeBtn);

        return view;
    }

    public CardView getCardView() {
        return mCardView;
    }
}
