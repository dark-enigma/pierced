package net.dark.pierced;

public class zoomlyhandling {
        private static boolean zooming;
        private static long zoomStart;

        public static boolean isZooming() {
            return zooming;
        }

        public static void setZooming(boolean state) {
            if(state && !zooming) {
                zoomStart = System.currentTimeMillis();
            }
            zooming = state;
        }

        public static double getZoomFactor() {
            if(!zooming) return 1.0;
            long elapsed = System.currentTimeMillis() - zoomStart;
            double progress = Math.min(elapsed / 300.0, 1.0);
            double nonLinearProgress = Math.sin(progress * (Math.PI / 2));
            return 1.0 - nonLinearProgress * (1.0 - 0.1);
        }
    }

