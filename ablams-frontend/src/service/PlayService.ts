import {Component} from "vue-property-decorator";
import AudiofileResource from "@/rest/AudiofileResource";
import ProgressRessource from "@/rest/ProgressRessource";
import AudioBookResource from "@/rest/AudioBookResource";
import {AudiobookDTO} from 'ablams-communication/ablams/communication';


class PlayService {
    public play( audio: HTMLAudioElement, abook: AudiobookDTO){
        if(abook.id != undefined) {
            ProgressRessource.fetchByAudioBookId(abook.id).then(e => {
                let abookProgress = e.data;
                audio.src = AudiofileResource.getStreamEndpointForAudioFile(abookProgress.trackNr);
                audio.currentTime = abookProgress.secondsInto;
                return audio.play()
            }).catch(e => {
                console.log("error starting progress" + e)
            });
        }
    }
}

export default new PlayService();
