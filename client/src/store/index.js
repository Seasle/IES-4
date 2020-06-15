import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        house: null,
        module: null
    },
    mutations: {
        updateHouse(state, payload) {
            state.house = payload;
        },
        updateModule(state, payload) {
            state.module = payload;
        }
    },
    actions: {
        updateHouse(context, data) {
            context.commit('updateHouse', data);
        },
        updateModule(context, data) {
            context.commit('updateModule', data);
        }
    }
});