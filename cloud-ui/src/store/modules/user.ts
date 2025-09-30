import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: null as any
  }),

  getters: {
    isLogin: (state) => !!state.token
  },

  actions: {
    setToken(token: string) {
      this.token = token;
      localStorage.setItem('token', token);
    },

    logout() {
      this.token = '';
      this.userInfo = null;
      localStorage.removeItem('token');
      // 可选：清除其他缓存
    },

    // 可选：从后端获取用户信息
    async fetchUserInfo() {
        
      // 模拟请求
      // const res = await api.getUserInfo();
      // this.userInfo = res.data;

    }
  }
});