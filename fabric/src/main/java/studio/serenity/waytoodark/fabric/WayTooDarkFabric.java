package studio.serenity.waytoodark.fabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import studio.serenity.waytoodark.WayTooDark;
@Environment(EnvType.CLIENT)
public class WayTooDarkFabric implements ClientModInitializer {
    @Override public void onInitializeClient() { WayTooDark.init(); }
}
