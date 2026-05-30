package studio.serenity.waytoodark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class WayTooDark {
    public static final String MOD_ID   = "way_too_dark";
    public static final String MOD_NAME = "Way Too Dark";
    public static final Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);
    private WayTooDark() {}
    public static void init() {
        LOGGER.info("[{}] Initialized. The night is absolute.", MOD_NAME);
    }
}
