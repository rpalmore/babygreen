import store from "../store/index.js";
import profileService from "./ProfileService.js";
import plantService from "./PlantService.js";
import plantNoteService from "./PlantNoteService.js";
import keys from "../../keys.js";

const myWidget = window.cloudinary.createUploadWidget(
    {
        cloudName: keys.keys.cloudName,
        uploadPreset: keys.keys.uploadPreset,
        sources: ["local", "url", "camera", "image_search", "google_drive", "unsplash", "facebook", "dropbox", "instagram"],
        googleApiKey: keys.keys.googleKey,
        showAdvancedOptions: true,
        searchByRights: true,
        cropping: true,
        multiple: false,
        defaultSource: "local",
        styles: {
            palette:
            {
                window: "#190B28",
                windowBorder: "#BACDB0",
                tabIcon: "#FFCB77",
                menuIcons: "#FFCB77",
                textDark: "#190B28",
                textLight: "#BACDB0",
                link: "#FDFFFC",
                action: "#FFCB77",
                inactiveTabIcon: "#FDFFFC",
                error: "B74F6F",
                inProgress: "#FFCB77",
                complete: "#20B832",
                sourceBg: "#190B28"
            },
        },
    },
    (error, result) => {
        if (!error && result && result.event === "success") {
            if (store.state.cloudinarySource === "profile") {
                let profile = {
                    userId: store.state.user.id,
                    displayName: store.state.profile.displayName,
                    profileImg: store.state.profile.profileImg,
                    favePlant: store.state.profile.favePlant,
                    skillLevel: store.state.profile.skillLevel
                };
                if (store.state.profile.favePlant === undefined && store.state.profile.profileImg === undefined) {
                    profile.profileImg = result.info.secure_url;
                    profileService
                        .createProfile(profile)
                        .then((response) => {
                            if (response.status === 201) {
                                store.commit("SET_PROFILE", profile);
                            }
                        })
                        .catch((err) => {
                            /* eslint no-console: ["error", { allow: ["error"] }] */
                            console.error(err + " problem creating profile!");
                            this.$router.push("/oops");
                        });
                } else {
                    profile.profileImg = result.info.secure_url;
                    profileService
                        .editProfile(profile)
                        .then((response) => {
                            if (response.status === 200) {
                                store.commit("SET_PROFILE", profile);
                            }
                        })
                        .catch((err) => {
                            /* eslint no-console: ["error", { allow: ["error"] }] */
                            console.error(err + " problem updating profile!");
                            this.$router.push("/oops");
                        });
                }
            } else if (store.state.cloudinarySource === 'plant') {
                let plant = store.state.object;
                plant.plantImg = result.info.secure_url;
                plantService.editPlant(plant).then((response) => {
                    if (response.status === 200) {
                        store.commit("EDIT_PLANT", plant);
                    }
                }).catch((err) => {
                    /* eslint no-console: ["error", { allow: ["error"] }] */
                    console.error(err + " problem updating plant!");
                    this.$router.push("/oops");
                });
            } else if (store.state.cloudinarySource === 'note') {
                let note = store.state.object;
                note.noteImg = result.info.secure_url;
                plantNoteService.editNote(note).then((response) => {
                    if (response.status === 200) {
                        store.commit("EDIT_NOTE", note);
                    }
                }).catch((err) => {
                    /* eslint no-console: ["error", { allow: ["error"] }] */
                    console.error(err + " problem updating note!");
                    this.$router.push("/oops");
                })
            }
        }
    });

export default {
    myWidget
}