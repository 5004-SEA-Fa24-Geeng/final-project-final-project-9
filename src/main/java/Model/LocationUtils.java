package Model;

public class LocationUtils {
    private static final double EARTH_RADIUS = 6371; // 地球半径，单位：公里

    /**
     * 计算两个经纬度坐标之间的距离（单位：公里）
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 两点之间的距离（公里）
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                  Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                  Math.sin(dLon/2) * Math.sin(dLon/2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return EARTH_RADIUS * c;
    }

    /**
     * 检查一个点是否在指定半径范围内
     * @param centerLat 中心点纬度
     * @param centerLon 中心点经度
     * @param pointLat 待检查点纬度
     * @param pointLon 待检查点经度
     * @param radius 半径（公里）
     * @return 是否在范围内
     */
    public static boolean isWithinRadius(double centerLat, double centerLon, 
                                       double pointLat, double pointLon, double radius) {
        return calculateDistance(centerLat, centerLon, pointLat, pointLon) <= radius;
    }

    /**
     * 验证经纬度是否有效
     * @param latitude 纬度
     * @param longitude 经度
     * @return 是否有效
     */
    public static boolean isValidCoordinates(double latitude, double longitude) {
        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180;
    }
} 