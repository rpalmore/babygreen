import store from "../store/index.js";
// import profileService from "./ProfileService.js";
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
                window: "#011627",
                windowBorder: "#2EC4b6",
                tabIcon: "#FF9F1C",
                menuIcons: "#FF9F1C",
                textDark: "#011627",
                textLight: "#FDFFFC",
                link: "#FDFFFC",
                action: "#FF9F1C",
                inactiveTabIcon: "#FDFFFC",
                error: "#E71D36",
                inProgress: "#FF9F1C",
                complete: "#20B832",
                sourceBg: "#011627"
            },
        },
    },
    (error, result) => {
        if (!error && result && result.event === "success") {
            // if widget was opened on profile page, do this:
            if (store.state.cloudinarySource === "profile") {
                let profileImg = result.info.secure_url;
                store.commit("SET_PROFILE_IMG", profileImg);
            }
        }
    });

// trying this out to see how it functions
// myWidget.close({quiet: true});

export default {
    myWidget
}