import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;

public class Bot extends ListenerAdapter {

    @Override
    public void onKick(KickEvent event) {
        if (event.getRecipient().getNick().equals(event.getBot().getNick())) {
            event.getBot().sendIRC().joinChannel(event.getChannel().getName());
        }
    }

    @Override
    public void onMessage(MessageEvent event) {
        System.out.println(event.getMessage());
        System.out.println(event.getUser().toString());
        // event.respondWith("String to reply with!");
    }

    public static void main(String[] args) {

        //Configure what we want our bot to do
        Configuration configuration = new Configuration.Builder()
                .setName("JeffBot")
                .addServer("irc.freenode.net", 6697)
                .setSocketFactory(new UtilSSLSocketFactory().trustAllCertificates())
                .addAutoJoinChannel("#purduelug")
                .addListener(new Bot())
                .buildConfiguration();

        //Create our bot with the configuration
        PircBotX bot = new PircBotX(configuration);

        //Connect to the server
        try {
            bot.startBot();
        } catch (Exception e) {
            System.err.println("FAILED TO START BOT");
            System.err.println(e.getMessage());
        }
    }
}
