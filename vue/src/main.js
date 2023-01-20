import Vue from 'vue'
import App from './App.vue'
import IdleVue from 'idle-vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import { AvatarPlugin, 
  BadgePlugin, 
  ButtonPlugin, 
  CardPlugin, 
  CollapsePlugin, 
  FormPlugin, 
  FormCheckboxPlugin, 
  FormGroupPlugin, 
  FormInputPlugin, 
  FormRadioPlugin, 
  FormSelectPlugin, 
  FormTextareaPlugin, 
  JumbotronPlugin, 
  LayoutPlugin, 
  LinkPlugin, 
  ListGroupPlugin, 
  ModalPlugin, 
  NavPlugin, 
  VBToggle, 
  VBTooltip, 
  BIconCameraFill, 
  BIconTrashFill, 
  BIconPencilFill, 
  BIconLink45deg, 
  BIconFilePost, 
  BIconGithub } from 'bootstrap-vue'

// Import Bootstrap and BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import "core-js/modules/es.promise";
import "core-js/modules/es.array.iterator";

Vue.use(AvatarPlugin)
Vue.use(BadgePlugin)
Vue.use(ButtonPlugin)
Vue.use(CardPlugin)
Vue.use(CollapsePlugin)
Vue.use(FormPlugin)
Vue.use(FormCheckboxPlugin)
Vue.use(FormGroupPlugin)
Vue.use(FormInputPlugin)
Vue.use(FormRadioPlugin)
Vue.use(FormSelectPlugin)
Vue.use(FormTextareaPlugin)
Vue.use(JumbotronPlugin)
Vue.use(LayoutPlugin)
Vue.use(LinkPlugin)
Vue.use(ListGroupPlugin)
Vue.use(ModalPlugin)
Vue.use(NavPlugin)
Vue.directive('b-tooltip', VBTooltip)
Vue.directive('b-toggle', VBToggle)
Vue.component('BIconCameraFill', BIconCameraFill)
Vue.component('BIconTrashFill', BIconTrashFill)
Vue.component('BIconPencilFill', BIconPencilFill)
Vue.component('BIconGithub', BIconGithub)
Vue.component('BIconLink45deg', BIconLink45deg)
Vue.component('BIconFilePost', BIconFilePost)

const eventsHub = new Vue()
 
Vue.use(IdleVue, { 
  eventEmitter: eventsHub,
  idleTime: 600000
 })

Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')