import store from "../store/index.js";
import profileService from "./ProfileService.js";
const cloudName = "dgupilxum";
const uploadPreset = "lbpxurzh";


const myWidget = window.cloudinary.createUploadWidget(
    {
        cloudName: cloudName,
        uploadPreset: uploadPreset,
        sources: ["local", "url", "camera", "image_search", "google_drive", "unsplash", "facebook", "dropbox", "instagram"],
        googleApiKey: "<image_search_google_api_key>",
        showAdvancedOptions: true,
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
                textLight: "BACDB0",
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
                            alert(err + " problem creating profile!");
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
                            alert(err + " problem updating profile!");
                        });
                }
            }
        }
    });

export default {
    myWidget
}