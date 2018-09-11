package cc.buddies.app.treasury.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import java.util.List;

public class CameraUtils {

    public static Camera getCamera() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            LogUtils.e("打开Camera失败失败");
        }
        return c;
    }

    /**
     * 检查是否有相机硬件
     * @param context 上下文
     * @return boolean
     */
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth
     *            需要被进行对比的原宽
     * @param surfaceHeight
     *            需要被进行对比的原高
     * @param preSizeList
     *            需要对比的预览尺寸列表
     * @param mIsPortrait
     *            是否屏幕竖直
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList, boolean mIsPortrait) {

        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (mIsPortrait) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
        } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for(Camera.Size size : preSizeList){
            if((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)){
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        LogUtils.e("surfaceWidth: " + surfaceWidth + "      surfaceHeight: " + surfaceHeight + "    ratio: " + reqRatio);
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
            LogUtils.e("(Camera.Size width: " + size.width + "      height: " + size.height + "    ratio: " + curRatio + "      deltaRatio: " + deltaRatio);
        }

        return retSize;
    }

    public static Camera.Size getPreSizeByWidth(int surfaceWidth, List<Camera.Size> preSizeList) {
        for(Camera.Size size : preSizeList){
            if (surfaceWidth == size.height) {
                return size;
            }
        }

        return null;
    }

}
