// 工具函数库
export const Utils = {
  /**
   * 数组去重
   * @description 根据指定的键对数组进行去重操作
   * @param {Array} array - 要去重的数组
   * @param {string} key - 去重的键名
   * @returns {Array} 去重后的新数组
   * @example
   * const uniqueArray = Utils.uniqueByKey([{id: 1}, {id: 1}, {id: 2}], 'id');
   */
  uniqueByKey: (array, key) => {
    if (!Array.isArray(array) || !key) {
      return array;
    }
    
    const seen = new Set();
    return array.filter(item => {
      const duplicate = seen.has(item[key]);
      seen.add(item[key]);
      return !duplicate;
    });
  },

  /**
   * 安全获取嵌套属性
   * @description 安全地获取对象的嵌套属性，避免因为某个层级不存在而报错
   * @param {Object} obj - 要获取属性的对象
   * @param {string} path - 属性路径，如 'a.b.c'
   * @param {*} defaultValue - 属性不存在时的默认值，默认为null
   * @returns {*} 获取到的属性值或默认值
   * @example
   * const value = Utils.safeGet(user, 'profile.address.city', '未知城市');
   */
  safeGet: (obj, path, defaultValue = null) => {
    if (!obj || typeof obj !== 'object' || !path) {
      return defaultValue;
    }
    
    return path.split('.').reduce((current, key) => {
      return current && current[key] !== undefined ? current[key] : defaultValue;
    }, obj);
  },

  /**
   * 格式化日期时间
   * @description 将日期对象格式化为指定格式的字符串
   * @param {Date|string|number} date - 日期对象、日期字符串或时间戳
   * @param {string} format - 日期格式，如 'yyyy-MM-dd HH:mm:ss'
   * @returns {string} 格式化后的日期字符串
   * @example
   * const formattedDate = Utils.formatDate(new Date(), 'yyyy-MM-dd');
   */
  formatDate: (date, format = 'yyyy-MM-dd HH:mm:ss') => {
    const d = new Date(date);
    if (isNaN(d.getTime())) {
      return '';
    }

    const o = {
      'M+': d.getMonth() + 1, // 月份
      'd+': d.getDate(), // 日
      'H+': d.getHours(), // 小时
      'm+': d.getMinutes(), // 分
      's+': d.getSeconds(), // 秒
      'q+': Math.floor((d.getMonth() + 3) / 3), // 季度
      'S': d.getMilliseconds() // 毫秒
    };

    if (/(y+)/.test(format)) {
      format = format.replace(RegExp.$1, (d.getFullYear() + '').substr(4 - RegExp.$1.length));
    }

    for (const k in o) {
      if (new RegExp('(' + k + ')').test(format)) {
        format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
      }
    }

    return format;
  },

  /**
   * 深拷贝
   * @description 创建一个对象的深拷贝
   * @param {*} source - 源对象
   * @returns {*} 拷贝后的对象
   * @example
   * const copy = Utils.deepClone(originalObject);
   */
  deepClone: (source) => {
    if (source === null || typeof source !== 'object') {
      return source;
    }

    if (source instanceof Date) {
      return new Date(source.getTime());
    }

    if (source instanceof Array) {
      return source.map(item => Utils.deepClone(item));
    }

    if (typeof source === 'object') {
      const cloned = {};
      for (const key in source) {
        if (source.hasOwnProperty(key)) {
          cloned[key] = Utils.deepClone(source[key]);
        }
      }
      return cloned;
    }

    return source;
  },

  /**
   * 数组对象转换为Map
   * @description 将数组对象转换为以指定键为key的Map对象
   * @param {Array} array - 源数组
   * @param {string} key - 作为Map键的属性名
   * @returns {Map} 转换后的Map对象
   * @example
   * const productMap = Utils.arrayToMap(products, 'id');
   */
  arrayToMap: (array, key) => {
    if (!Array.isArray(array) || !key) {
      return new Map();
    }

    const map = new Map();
    array.forEach(item => {
      if (item && item[key] !== undefined) {
        map.set(item[key], item);
      }
    });
    return map;
  },

  /**
   * Map转换为数组
   * @description 将Map对象转换为数组
   * @param {Map} map - 源Map对象
   * @returns {Array} 转换后的数组
   * @example
   * const productArray = Utils.mapToArray(productMap);
   */
  mapToArray: (map) => {
    if (!(map instanceof Map)) {
      return [];
    }

    return Array.from(map.values());
  },

  /**
   * 防抖函数
   * @description 创建一个防抖函数，在事件被触发n秒后再执行回调
   * @param {Function} func - 要防抖的函数
   * @param {number} wait - 等待时间(毫秒)
   * @param {boolean} immediate - 是否立即执行
   * @returns {Function} 防抖后的函数
   * @example
   * const debouncedSearch = Utils.debounce(searchFunction, 300);
   */
  debounce: (func, wait, immediate = false) => {
    let timeout;
    
    return function executedFunction(...args) {
      const later = () => {
        timeout = null;
        if (!immediate) func.apply(this, args);
      };
      
      const callNow = immediate && !timeout;
      clearTimeout(timeout);
      timeout = setTimeout(later, wait);
      
      if (callNow) func.apply(this, args);
    };
  },

  /**
   * 节流函数
   * @description 创建一个节流函数，规定在一个单位时间内，只能触发一次函数
   * @param {Function} func - 要节流的函数
   * @param {number} limit - 时间限制(毫秒)
   * @returns {Function} 节流后的函数
   * @example
   * const throttledScroll = Utils.throttle(scrollFunction, 200);
   */
  throttle: (func, limit) => {
    let inThrottle;
    
    return function(...args) {
      const context = this;
      
      if (!inThrottle) {
        func.apply(context, args);
        inThrottle = true;
        setTimeout(() => inThrottle = false, limit);
      }
    };
  }
}

// 调试工具
export const DEBUG = {
  enabled: process.env.NODE_ENV !== 'production',
  log: (...args) => DEBUG.enabled && console.log('[DEBUG]', ...args),
  warn: (...args) => DEBUG.enabled && console.warn('[DEBUG] WARNING:', ...args),
  error: (...args) => DEBUG.enabled && console.error('[DEBUG] ERROR:', ...args)
}

// 导出默认工具
const utils = {
  ...Utils,
  DEBUG
}

export default utils