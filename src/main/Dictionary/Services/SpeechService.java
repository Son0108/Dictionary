package main.Dictionary.Services;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
public class SpeechService {
    public static void TalkResource(String sayText) {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice("kevin16");
        voice.allocate();
        try {
            voice.speak(sayText);
        }catch (Exception e){
            try {
                throw e;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
