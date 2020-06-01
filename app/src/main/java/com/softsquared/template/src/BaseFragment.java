package com.softsquared.template.src;
import android.app.ProgressDialog;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.softsquared.template.R;

public class BaseFragment extends Fragment {
    public ProgressDialog mProgressDialog;
    public final String str[] = {
            "음식점", "제과", "약국", "숙박",
            "미용", "의류", "병원", "보험",
            "기타 의료", "문화", "주유", "유통",
            "서적", "학원", "사무통신", "자동차판매",
            "서비스", "여행", "레저", "취미",
            "음료", "위생", "생활", "문구"
    };
    public void showCustomToast(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    public class Place{
        String title;
        String address;
        String tel;
        double lat, lon;

        public Place(String title, String address, String tel, double lat, double lon) {
            this.title = title;
            this.address = address;
            this.tel = tel;
            this.lat = lat;
            this.lon = lon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}
