package com.deu.multisolvermko.information;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.authentication.SignInActivity;
import com.deu.multisolvermko.information.adapters.InformationAdapter;
import com.deu.multisolvermko.information.items.InformationItem;
import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity {

    private InformationAdapter informationAdapter;
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
        onboardingViewPager.setAdapter(informationAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(v -> {
            if (onboardingViewPager.getCurrentItem() + 1 < informationAdapter.getItemCount()){
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);

            }else{
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });
    }

    private void setupOnboardingItems(){

        List<InformationItem> informationItems = new ArrayList<>();

        InformationItem userProfile = new InformationItem();
        userProfile.setTitle("Profil Fotoğrafı Değiştirmek");
        userProfile.setDescription("Profil fotoğrafını değiştirmek için sol taraftan açılan menüde bulunan profil fotoğrafı kısmına uzun dokununuz.");
        userProfile.setImage(R.drawable.profile);

        InformationItem salesmanProblem = new InformationItem();
        salesmanProblem.setTitle("Gezgin Satıcı Problemi");
        salesmanProblem.setDescription("Bu problem uygulama ile birlikte gelmektedir. Detaylı bilgilendirme ilerleyen aşamalarda yapılacaktır.");
        salesmanProblem.setImage(R.drawable.guide_image_1);

        InformationItem salesmanProblem2 = new InformationItem();
        salesmanProblem2.setTitle("Gezgin Satıcı Problemi (Manuel)");
        salesmanProblem2.setDescription("Bu problem uygulama ile birlikte gelmektedir. Detaylı bilgilendirme ilerleyen aşamalarda yapılacaktır.");
        salesmanProblem2.setImage(R.drawable.guide_image_2);

        InformationItem futureUpdates = new InformationItem();
        futureUpdates.setTitle("Gelecek Güncellemeler");
        futureUpdates.setDescription("Gelecek güncellemeler burada belirtilecektir.");
        futureUpdates.setImage(R.drawable.guide_image_3);

        informationItems.add(userProfile);
        informationItems.add(salesmanProblem);
        informationItems.add(salesmanProblem2);
        informationItems.add(futureUpdates);

        informationAdapter = new InformationAdapter(informationItems);

    }
    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[informationAdapter.getItemCount()];
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

        if (index == informationAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Başla");
        }else{
            buttonOnboardingAction.setText("İleri");
        }
    }
}