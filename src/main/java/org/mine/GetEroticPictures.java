package org.mine;


import io.ktor.client.HttpClient;
import io.ktor.client.features.Sender;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import net.mamoe.mirai.console.command.CommandSenderOnMessage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public final class GetEroticPictures extends JavaPlugin {
    public static final GetEroticPictures INSTANCE = new GetEroticPictures();

    private GetEroticPictures() {
        super(new JvmPluginDescriptionBuilder("org.mine.getEroticPictures", "1.0-SNAPSHOT").build());
    }


    @Override
    public void onEnable() {
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            if (event.getMessage().contentToString().matches(".*help.*|.*二次元.*|.*r4.*|.*r15.*|.*pc.*|.*sj.*|.*bz.*|.*银.*|.*兽.*|.*r2.*|.*r1.*|.*坏次元.*|.*风景.*|.*三次元.*|.*pc壁纸.*|.*头像.*")) {
                String message = event.getMessage().contentToString();
                if (message.matches(".*help.*")){
                    event.getSubject().sendMessage(
                            "现在命令不需要/了捏" + "\n" +
                            "1. 日历: 出行|日历|节气|黄历" + "\n" +
                            "2. 国内新闻盲盒(热搜榜前30): 境内剑政|境内键政|境内剑震|境内键政|jnjz" + "\n" +
                            "3. 国外新闻查询: 不给看" + "\n" +
                            "4. 图片盲盒: 二次元|r4|r15|pc|sj|bz|银|兽|r2|r1|坏次元|风景|三次元|pc壁纸|头像"
                    );
                }
                String url = null;
                if (message.contains("r15")) {
                    url = "http://iw233.fgimax2.fgnwctvip.com/API/Ghs.php?type=";
                }else if (message.contains("r2")){
                    url = "https://api.yimian.xyz/img?type=moe";
                }else if (message.contains("r1")) {
                    url = "http://www.dmoe.cc/random.php";
                }else if (message.contains("pc壁纸")){
                    url = "https://api.yimian.xyz/img?type=moe&size=1920x1080";
                }else if (message.contains("pc")){
                    url = "https://iw233.cn/API/pc.php?type=";
                }else if (message.contains("银")) {
                    url = "https://iw233.cn/API/Yin.php?type=";
                }else if (message.contains("兽")){
                    url = "https://iw233.cn/api/Cat.php?type=";
                }else if(message.contains("坏次元")){
                    url = "https://tuapi.eees.cc/api.php?category=dongman&type=302";
                }else if(message.contains("风景")){
                    url = "https://tuapi.eees.cc/api.php?category={biying,fengjing}&type=302";
                }else if (message.contains("三次元")){
                    url = "https://tuapi.eees.cc/api.php?category=meinv&type=302";
                }else if (message.contains("r4")) {
                    url = "https://iw233.cn/API/MirlKoi-iw233.php?type=";
                }else if (message.contains("bz")) {
                    url = "https://iw233.cn/API/MirlKoi.php?type=";
                }else if (message.contains("sj")){
                    url = "https://iw233.cn/API/mp.php?type=";
                }else if (message.contains("二次元")){
                    url = "https://iw233.cn/API/Random.php?type=";
                }else if (message.contains("头像")){
                    url = "https://api.yimian.xyz/img?type=head";
                }
                if (url != null) {
                    try {
                        ExternalResource ex = ExternalResource.Companion.create(getUrlByByte(url));
                        Image img = ExternalResource.uploadAsImage(ex, event.getSubject());
                        MessageChain chain = new MessageChainBuilder()
                                .append(img)
                                .build();
                        event.getSubject().sendMessage(chain);
                        ex.close();
                    } catch (IOException e) {
                        event.getSubject().sendMessage("图片发送错误");
                        e.printStackTrace();
                    }
                }
            }
        });
        getLogger().info("Plugin loaded!");
    }
    /**
     * httpclient，获取url
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getUrlByByte(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "keep-alive")
                .build();

        return client.newCall(request).execute().body().bytes();
    }
}