import {Component} from "vue-property-decorator";
import AudiofileResource from "@/rest/AudiofileResource";
import ProgressRessource from "@/rest/ProgressRessource";
import AudioBookResource from "@/rest/AudioBookResource";
import {AudiobookDTO} from 'ablams-communication/ablams/communication';


class PlayService {
    public play( audio: HTMLAudioElement, abook: AudiobookDTO){
        ProgressRessource.fetchByAudioBookId(abook.id).then(e => {
            let abookProgress = e.data;
            audio.src = AudiofileResource.getStreamEndpointForAudioFile(abookProgress.afileId);
            audio.currentTime = abookProgress.secondsInto;
            return audio.play()
        });
    }
}

export default new PlayService();
