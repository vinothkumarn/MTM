import com.google.inject.Guice;
import com.google.inject.Injector;
import money.transfer.config.MtmModule;

public class CustomApplicationLauncher {

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new MtmModule());
    }
}
