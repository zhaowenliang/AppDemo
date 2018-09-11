package cc.buddies.app.appdemo.mvp.presenter;

import cc.buddies.app.appdemo.mvp.model.Callback;
import cc.buddies.app.appdemo.mvp.model.MvpModel;
import cc.buddies.app.appdemo.mvp.view.MvpView;

public class MvpPresenter extends BasePresenter<MvpView> {

    public void getData(String params) {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        MvpModel.getNetData(params, new Callback<String>() {
            @Override
            public void onSuccess(String data) {
                if (isViewAttached()) {
                    getView().showData(data);
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    getView().showToast(msg);
                }
            }

            @Override
            public void onError() {
                if (isViewAttached()) {
                    getView().showErr();
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    getView().hideLoading();
                }
            }
        });
    }

}
