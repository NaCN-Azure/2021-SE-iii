import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'

Vue.config.devtools = true

import '@/permission'
Vue.config.productionTip = false
const options = {
  namespace: 'pro__',
  name: 'ls',
  storage: 'local'
}

Vue.use(ElementUI, {locale})

Vue.directive('title', {
  inserted: function (el, binding) {
    document.title = el.dataset.title
  }
})
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
