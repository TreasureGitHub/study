package test;

/**
 * @author lff
 * @datetime 2021/01/20 23:31
 */
public class Search {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 10};
        System.out.println(search1(arr, 10));
    }

    /**
     * 二分查找
     *
     * @param arr   待查找的数组，升序排序
     * @param value 待查找的数值
     * @return
     */
    public static int search(int[] arr, int value) {
        int start = 0;
        int end = arr.length - 1;
        int mid;

        while (start <= end) {
            mid = (start + end) / 2;
            if (value == arr[mid]) {
                return mid;
            } else if (value > arr[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return -1;
    }

    public static int search1(int[] arr, int value) {
        int mid = 0;
        return search(arr, value, 0, arr.length - 1);
    }

    private static int search(int[] arr, int value, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (value == arr[mid]) {
            return mid;
        } else if (value > arr[mid]) {
            return search(arr, value, mid + 1, end);
        } else {
            return search(arr, value, start, mid - 1);
        }
    }
}