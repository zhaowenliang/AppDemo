package cc.buddies.app.appdemo;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

//        new HelloB();


//        System.out.println("斐波那契数列的前20项为：");
//        long currentTimeMillis = System.currentTimeMillis();
        // 循环方式
//        testFibonacci1(45);
        // 递归方式
//        for (int j = 1; j <= 45; j++) {
//            System.out.print(getFibo(j) + "\t");
//            if (j % 5 == 0)
//                System.out.println();
//        }

        // 快速排序
//        quickSort();
//        System.out.println("使用时间： " + (System.currentTimeMillis() - currentTimeMillis));
        // 递归45项9518毫秒


        DemoFuture demoFuture = new DemoFuture();
        demoFuture.demo();
    }



    public  void testFibonacci1(int n) {
        int a = 1, b = 1, c = 0;
        for (int i = 1; i <= n; i++) {
            if (i == 1) {
                System.out.println("第" + i + "等于" + a);
            } else if (i == 2) {
                System.out.println("第" + i + "等于" + b);
            } else {
                c = a + b;
                a = b;
                b = c;
                System.out.println("第" + i + "等于" + c);
            }
        }
    }


    // 使用递归方法
    private static int getFibo(int i) {
        if (i == 1 || i == 2) {
            return 1;
        } else {
            return getFibo(i - 1) + getFibo(i - 2);
        }
    }

    private void quickSort() {
        Integer[] nums = {10,58,72,5,9,7,45,15};//需要排序的数组
        nums = sort(nums,0,nums.length-1);
        System.out.println(Arrays.toString(nums));
    }

    // 快速排序
    public  Integer[] sort(Integer[] nums,Integer left,Integer right){
        int i,j,t,temp;
        if(left>right){
            return nums;
        }else{
            //获取基准数
            temp = nums[left];
            i = left;
            j = right;
            //从右边往左遍历,获取第一个小于基准数的下标
            while(nums[j]>=temp && i<j){
                j--;
            }

            //从左往右遍历，获取第一个大于基准数的下标
            while(nums[i]<=temp && i<j){
                i++;
            }
            //然后交换两个数在数组中的位置
            if(i<j){//判断两数下标有没有相遇
                //若没有相遇
                //交换两数的位置
                t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
                return sort(nums,left,right);
            }else{
                nums[left] = nums[i];
                nums[i] = temp;
                sort(nums, left, i-1);
                sort(nums, j+1, right);
                return nums;
            }
        }
    }


}