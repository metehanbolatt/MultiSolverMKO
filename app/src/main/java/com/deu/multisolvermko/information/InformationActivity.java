package com.deu.multisolvermko.information;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.authentication.SignInActivity;
import com.deu.multisolvermko.information.adapters.OnboardingAdapter;
import com.deu.multisolvermko.information.items.OnboardingItem;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private Button buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);

                }else{
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                }
            }
        });
    }

    private void setupOnboardingItems(){

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemProfile = new OnboardingItem();
        itemProfile.setTitle("Profil Resmi Değiştirme");
        itemProfile.setDescription("Profil fotoğrafınızı değiştirmek için sol taraftan açılan menüde fotoğrafınıza uzun tıklayabilirsiniz.");
        itemProfile.setImage(R.drawable.profile);

        OnboardingItem itemPayOnline = new OnboardingItem();
        itemPayOnline.setTitle("Gezgin Satıcı Problemi");
        itemPayOnline.setDescription("Gezgin satıcı problemi 10 şehirden oluşan bir problemdir." +
                " Bu problemin çözümünde bulunan şehirler arası uzaklık verileri programa gömülüdür. Bizimle iletişime geçerek kendinize ait veri setini iletebilirsiniz.");
        itemPayOnline.setImage(R.drawable.pngg);

        OnboardingItem itemOnTheWay = new OnboardingItem();
        itemOnTheWay.setTitle("Gezgin Satıcı Problemi (Distances)");
        itemOnTheWay.setDescription("Kaç şehrinizin olduğunu ve şehirler arası mesafelerin ne kadar olduğunu kendiniz girmek isterseniz izlemeniz gereken yol, Öncelikle kaç şehrinizin olduğunu girmek" +
                " Ardından butona tıklayıp sırasıya şehirler arası mesafeleri girmek. Detaylı bilgiyi mevcut sayfada bulabilirsiniz.");
        itemOnTheWay.setImage(R.drawable.pnggg);

        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("Gelecek güncellemeler.");
        itemEatTogether.setDescription("Gelecek problem güncellemeleri ile bu bilgilendirme sayfası artacaktır.");
        itemEatTogether.setImage(R.drawable.pngggg);

        onboardingItems.add(itemProfile);
        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOnTheWay);
        onboardingItems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }
    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);

        }
    }
    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView)layoutOnboardingIndicators.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                );
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                );
            }
        }

        if (index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }else{
            buttonOnboardingAction.setText("Next");
        }

    }
}