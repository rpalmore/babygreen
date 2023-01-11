import Vue from 'vue'
import Router from 'vue-router'
const Login = () => import('../views/Login.vue')
const Logout = () => import('../views/Logout.vue')
const Register = () => import('../views/Register.vue')
const Profile = () => import('../views/Profile.vue')
const AddPlant = () => import('../views/AddPlant.vue')
const Plants = () => import('../views/Plants.vue')
const PlantDetail = () => import('../components/PlantDetail.vue')
const Care = () => import('../views/Care.vue')
const About = () => import('../views/About.vue')
const Gallery = () => import('../views/Gallery.vue')
const Reset = () => import('../views/Reset.vue')
const Settings = () => import('../views/Settings.vue')
import store from '../store/index'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/reset",
      name: "password-reset",
      component: Reset,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/about",
      name: "about",
      component: About,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/",
      name: "about",
      component: About,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/profile",
      name: "profile",
      component: Profile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/settings",
      name: "settings",
      component: Settings,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/plants",
      name: "plants",
      component: Plants,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/add-plant",
      name: "add-plant",
      component: AddPlant,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/plants/:plantId",
      name: "plant-detail",
      component: PlantDetail,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/plant-care",
      name: "care",
      component: Care,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/gallery",
      name: "gallery",
      component: Gallery,
      meta: {
        requiresAuth: true
      }
    },
  ],
  scrollBehavior() {
    document.getElementById('app').scrollIntoView({ behavior: "smooth" });
  }
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});
export default router;
