const state = {
  section: 'Home'
}

const mutations = {
  section (value) {
    state.section = value
  }
}

const appStore = {
  state,
  mutations
}

export default appStore
