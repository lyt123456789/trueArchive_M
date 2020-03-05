(function() {
  var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
 
  Math.uuid = function (len, radix) {
    var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;
    if (len) {
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      var r;
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
       for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
    return uuid.join('');
  };

  Math.uuidFast = function() {
    var chars = CHARS, uuid = new Array(36), rnd=0, r;
    for (var i = 0; i < 36; i++) {
      if (i==8 || i==13 ||  i==18 || i==23) {
        uuid[i] = '-';
      } else if (i==14) {
        uuid[i] = '4';
      } else {
        if (rnd <= 0x02) rnd = 0x2000000 + (Math.random()*0x1000000)|0;
        r = rnd & 0xf;
        rnd = rnd >> 4;
        uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
      }
    }
    return uuid.join('');
  };

  Math.uuidCompact = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
      return v.toString(16);
    });
  };
})();

var com = {};
com.xjwgraph = {};

var PathGlobal = com.xjwgraph.PathGlobal = {
    pointTypeLeftUp: 1,
    pointTypeUp: 2,
    pointTypeUpRight: 3,
    pointTypeLeft: 4,
    pointTypeRight: 5,
    pointTypeLeftDown: 6,
    pointTypeDown: 7,
    pointTypeDownRight: 8,
    lineDefIndex: 10,
    lineDefStep: 2,
    modeDefIndex: 10,
    modeDefStep: 2,
    modeInc: 3,
    pauseTime: 10,
    modeHeigh: 0,
    copyModeDec: 10,
    rightMenu: false,
    isPixel: true,
    maxEvent: 17,
    minHeight: 32,
    minWidth: 32,
    selectColor: "C5E7F6",
    clearBoderColor: "blue",
    defaultColor: "green",
    defaultMaxMag: 0.5,
    defaultMinMag: 2,
    lineColor: "teal",                                //流程图链接线颜色
    lineCheckColor: "red",                              //流程图链接线选中颜色
    strokeweight: 1,
    dragPointDec: 3,
    switchType: false,
    newGraph: "\u65b0\u5efa\u56fe\u5c42",
    modeCreate: "\u521b\u5efa\u6a21\u5143",
    lineCreate: "\u521b\u5efa\u7ebf\u5143",
    modeMove: "\u79fb\u52a8\u6a21\u5143",
    lineMove: "\u79fb\u52a8\u7ebf\u5143",
    modeDragPoint: "\u7f29\u653e\u6a21\u5143",
    updateMode: "\u7f16\u8f91\u6a21\u5143",
    updateLine: "\u7f16\u8f91\u7ebf\u5143",
    copyMode: "\u62f7\u8d1d\u6a21\u5143",
    removeMode: "\u5220\u9664\u6a21\u5143",
    remodeLine: "\u5220\u9664\u7ebf\u5143",
    baseClear: "\u9009\u62e9\u6a21\u5143",
    clearContext: "\u6e05\u9664\u533a\u57df",
    contextDivDrag: "\u79fb\u52a8\u533a\u57df",
    toLeft: "\u5de6\u5bf9\u9f50",
    toRight: "\u53f3\u5bf9\u9f50",
    toMiddleWidth: "\u5782\u76f4\u5c45\u4e2d",
    toTop: "\u9876\u5bf9\u9f50",
    toMiddleHeight: "\u6c34\u5e73\u5c45\u4e2d",
    toBottom: "\u5e95\u5bf9\u9f50",
    buildLineAndMode: "\u7ed1\u5b9a\u7ebf\u5143",
    removeLineAndMode: "\u79fb\u9664\u7ed1\u5b9a",
    eventName: "\u89e6\u53d1\u4e8b\u4ef6",
    baseProTitle: "\u5de5\u4f5c\u6d41\u5c5e\u6027",
    lineProTitle: "\u8fde\u7ebf\u5c5e\u6027",
    modeProTitle: "\u8282\u70b9\u5c5e\u6027",
    editProp: "\u7f16\u8f91\u5c5e\u6027",
    pngImg:'flow/images/png.png'
};
/** 
 * 历史操作工厂类
 * * */
var UndoRedoEventFactory = com.xjwgraph.UndoRedoEventFactory = function() {
    var a = this;
    /** 历史记录栈* */
    a.stack = [];
    /** 当前记录位置* */
    a.index = 0;
    /**
     * 工具栏-重做按钮-点击事件
     */
    a.redo = function() {
        stopEvent = true;
        var d = a.stack[a.index];
        if (d) {
            d.redo();
            this.index++
        }
        var c = a.stack.length;
        if (this.index > c) {
            this.index = c
        }
    };
    /**
     * 工具栏-撤销按钮-点击事件
     */
    a.undo = function() {
        stopEvent = true;
        var c = a.stack[a.index - 1];
        if (c) {
            c.undo();
            a.index--
        }
        if (this.index < 1) {
            a.index = 1
        }
    };
    /**
     * 记录历史操作
     */
    a.addEvent = function(c) {
        a.stack.splice(a.index, (a.stack.length - a.index++), c);
        if (a.stack.length > PathGlobal.maxEvent) {
            a.stack.splice(0, 1);
            this.index = PathGlobal.maxEvent
        }
    }
};
/** 历史操作工厂类原型链* */
UndoRedoEventFactory.prototype = {
    /**
     * 工厂类初始化
     **/
    init: function() {
        var a = new com.xjwgraph.UndoRedoEvent(function() {},
        PathGlobal.newGraph);
        a.setRedo(function() {})
    }
};
/**
 * 历史记录元素对象
 */
var UndoRedoEvent = com.xjwgraph.UndoRedoEvent = function(c, b) {
    var a = this;
    a.name = b ? b: PathGlobal.eventName;
    a.undo = c;
    a.redo;
    /** 加入历史记录* */
    com.xjwgraph.Global.undoRedoEventFactory.addEvent(a);
    /** 设定撤销事件* */
    a.setUndo = function(d) {
        a.undo = d
    };
    /** 设定重做事件* */
    a.setRedo = function(d) {
        a.redo = d;
    }
};

/**
 * 完成绘图区设置
 */
var BaseTool = com.xjwgraph.BaseTool = function(c, d, a) {
    var b = this;
    b.pathBody = c;
    b.checkColor = "#00ff00";
    b.areaDiv = document.createElement("div");
    b.initEndDiv(d, a);
    b.initPathBody(b.pathBody);
    b.contextMoveAbale = false;
    b.contextMoveAttempt = false;
    b.contextMap = new Map();
    b.tempContextId = null;
    b.checkBrowser()
};
BaseTool.prototype = {
    initScaling: function(a) {
        var b = this;
        b.forEach(b.contextMap,
        function(e) {
            var f = $id(e),
            i = f.style;
            i.top = 0 + "px";
            i.left = 0 + "px";
            var h = 0,
            d = 0,
            g = b.contextMap.get(e).contextModeMap;
            b.forEach(g,
            function(q) {
                var o = $id(q),
                l = o.style,
                r = parseInt(l.top),
                k = parseInt(l.left),
                n = parseInt(o.offsetWidth),
                p = parseInt(o.offsetHeight),
                j = parseInt(i.top),
                m = parseInt(i.left);
                if (j > r || j == 0) {
                    i.top = r + "px"
                }
                if (m > k || m == 0) {
                    i.left = k + "px"
                }
                if (h < n + k) {
                    h = n + k
                }
                if (d < p + r) {
                    d = p + r
                }
            });
            i.width = h - parseInt(i.left) + "px";
            i.height = d - parseInt(i.top) + "px"
        })
    },
    /**
     * 返回需要对齐元素
     * 如果b区域存在，返回b区域内元素
     * 如果不存在，返回整个绘图区内所有元素
     **/
    getOptionMap: function(b) {
        var a = this,
        e = null;
        if (b) {
            e = a.contextMap.get(b).contextModeMap
        } else {
            var c = com.xjwgraph.Global;
            e = new Map();
            e.putAll(c.modeMap);
            var d = c.baseTool;
            d.forEach(d.contextMap,
            function(f) {
                var h = d.contextMap.get(f).contextModeMap,
                g = $id(f),
                i = g.getAttribute("groups");
                d.forEach(h,
                function(j) {
                    if (i == "true" || i) {
                        e.remove(j)
                    }
                })
            })
        }
        return e
    },
    /**
     * 左对齐操作
     **/
    toLeft: function() {
        var i = this,
        b = i.getOptionMap(i.tempContextId),
        d = -1;
        // 获得最左边元素距离
        i.forEach(b,
        function(l) {
            var k = $id(l),
            j = parseInt(k.style.left);
            if (d > j || d == -1) {
                d = j
            }
        });
        var c = com.xjwgraph.Global,
        g = c.modeTool,
        f = new Map(),
        e = new Map();
        // 完成元素左对齐步骤
        i.forEach(b,
        function(l) {
            var k = $id(l),
            j = k.style;
            f.put(l, parseInt(j.left));
            j.left = d + "px";
            e.put(l, parseInt(j.left));
            g.showPointer(k);
            g.changeBaseModeAndLine(k, true);
        });
        // 在历史步骤中添加左对齐
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            i.forEach(b,
            function(k) {
                var j = $id(k);
                j.style.left = f.get(k) + "px";
                g.showPointer(j);
                g.changeBaseModeAndLine(j, true);
            })
        },
        PathGlobal.toLeft);
        a.setRedo(function() {
            i.forEach(b,
            function(k) {
                var j = $id(k);
                j.style.left = e.get(k) + "px";
                g.showPointer(j);
                g.changeBaseModeAndLine(j, true);
            })
        })
    },
    toMiddleWidth: function() {
        var m = this,
        b = m.getOptionMap(m.tempContextId),
        l = [],
        g = 0;
        m.forEach(b,
        function(n) {
            var i = $id(n);
            l[g++] = parseInt(i.style.left) + parseInt(parseInt(i.offsetWidth) / 2)
        });
        l = l.sort(function(n, i) {
            return n - i
        });
        var d = parseInt(l.length / 2),
        f = l[d],
        c = com.xjwgraph.Global,
        j = c.modeTool,
        h = new Map(),
        e = new Map();
        m.forEach(b,
        function(p) {
            var o = $id(p),
            n = o.style;
            h.put(p, parseInt(n.left));
            var i = parseInt(parseInt(n.left) + parseInt(parseInt(o.offsetWidth) / 2) - f);
            n.left = parseInt(parseInt(n.left) - i) + "px";
            e.put(p, parseInt(n.left));
            j.showPointer(o);
            j.changeBaseModeAndLine(o, true);
        });
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            m.forEach(b,
            function(n) {
                var i = $id(n);
                i.style.left = h.get(n) + "px";
                j.showPointer(i);
                j.changeBaseModeAndLine(i, true);
            })
        },
        PathGlobal.toMiddleWidth);
        a.setRedo(function() {
            m.forEach(b,
            function(n) {
                var i = $id(n);
                i.style.left = e.get(n) + "px";
                j.showPointer(i);
                j.changeBaseModeAndLine(i, true);
            })
        })
    },
    toRight: function() {
        var i = this,
        b = i.getOptionMap(i.tempContextId),
        h = -1;
        i.forEach(b,
        function(l) {
            var k = $id(l),
            j = parseInt(k.style.left) + parseInt(k.offsetWidth);
            if (h < j) {
                h = j
            }
        });
        var c = com.xjwgraph.Global,
        f = c.modeTool,
        e = new Map(),
        d = new Map();
        i.forEach(b,
        function(m) {
            var l = $id(m),
            k = l.style;
            e.put(m, parseInt(k.left));
            var j = parseInt(k.left) + parseInt(l.offsetWidth);
            k.left = (h - j) + parseInt(k.left) + "px";
            d.put(m, parseInt(k.left));
            f.showPointer(l);
            f.changeBaseModeAndLine(l, true);
        });
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            i.forEach(b,
            function(k) {
                var j = $id(k);
                j.style.left = e.get(k) + "px";
                f.showPointer(j);
                f.changeBaseModeAndLine(j, true);
            })
        },
        PathGlobal.toRight);
        a.setRedo(function() {
            i.forEach(b,
            function(k) {
                var j = $id(k);
                j.style.left = d.get(k) + "px";
                f.showPointer(j);
                f.changeBaseModeAndLine(j, true);
            })
        })
    },
    toTop: function() {
        var i = this,
        b = i.getOptionMap(i.tempContextId),
        f = -1;
        i.forEach(b,
        function(l) {
            var k = $id(l),
            j = parseInt(k.style.top);
            if (f > j || f == -1) {
                f = j
            }
        });
        var c = com.xjwgraph.Global,
        e = c.modeTool,
        h = new Map(),
        d = new Map();
        i.forEach(b,
        function(l) {
            var k = $id(l),
            j = k.style;
            h.put(l, parseInt(j.top));
            j.top = f + "px";
            d.put(l, parseInt(j.top));
            e.showPointer(k);
            e.changeBaseModeAndLine(k, true);
        });
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            i.forEach(b,
            function(k) {
                var j = $id(k);
                j.style.top = h.get(k) + "px";
                e.showPointer(j);
                e.changeBaseModeAndLine(j, true);
            })
        },
        PathGlobal.toTop);
        a.setRedo(function() {
            i.forEach(b,
            function(k) {
                var j = $id(k);
                j.style.top = d.get(k) + "px";
                e.showPointer(j);
                e.changeBaseModeAndLine(j, true);
            })
        })
    },
    toMiddleHeight: function() {
        var m = this,
        b = m.getOptionMap(m.tempContextId),
        e = [],
        g = 0;
        this.forEach(b,
        function(n) {
            var i = $id(n);
            e[g++] = parseInt(i.style.top) + parseInt(parseInt(i.offsetHeight) / 2)
        });
        e = e.sort(function(n, i) {
            return n - i
        });
        var d = parseInt(e.length / 2),
        f = e[d],
        c = com.xjwgraph.Global,
        j = c.modeTool,
        l = new Map(),
        h = new Map();
        m.forEach(b,
        function(p) {
            var o = $id(p),
            i = o.style,
            n = parseInt(parseInt(i.top) + parseInt(parseInt(o.offsetHeight) / 2) - f);
            l.put(p, parseInt(i.top));
            i.top = parseInt(parseInt(i.top) - n) + "px";
            h.put(p, parseInt(i.top));
            j.showPointer(o);
            j.changeBaseModeAndLine(o, true);
        });
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            m.forEach(b,
            function(n) {
                var i = $id(n);
                i.style.top = l.get(n) + "px";
                j.showPointer(i);
                j.changeBaseModeAndLine(i, true);
            })
        },
        PathGlobal.toMiddleHeight);
        a.setRedo(function() {
            m.forEach(b,
            function(n) {
                var i = $id(n);
                i.style.top = h.get(n) + "px";
                j.showPointer(i);
                j.changeBaseModeAndLine(i, true);
            })
        })
    },
    toBottom: function() {
        var i = this,
        c = i.getOptionMap(i.tempContextId),
        a = -1;
        i.forEach(c,
        function(l) {
            var k = $id(l),
            j = parseInt(k.style.top) + parseInt(k.offsetHeight);
            if (a < j) {
                a = j
            }
        });
        var d = com.xjwgraph.Global,
        f = d.modeTool,
        h = new Map(),
        e = new Map();
        i.forEach(c,
        function(m) {
            var l = $id(m),
            j = l.style,
            k = parseInt(j.top) + parseInt(l.offsetHeight);
            h.put(m, parseInt(j.top));
            j.top = (a - k) + parseInt(j.top) + "px";
            e.put(m, parseInt(j.top));
            f.showPointer(l);
            f.changeBaseModeAndLine(l, true);
        });
        var b = new com.xjwgraph.UndoRedoEvent(function() {
            i.forEach(c,
            function(k) {
                var j = $id(k);
                j.style.top = h.get(k) + "px";
                f.showPointer(j);
                f.changeBaseModeAndLine(j, true);
            })
        },
        PathGlobal.toBottom);
        b.setRedo(function() {
            i.forEach(c,
            function(k) {
                var j = $id(k);
                j.style.top = e.get(k) + "px";
                f.showPointer(j);
                f.changeBaseModeAndLine(j, true);
            })
        })
    },
    sumLeftTop: function(a, b, c) {
        if (!b) {
            b = a.offsetLeft
        }
        if (!c) {
            c = a.offsetTop
        }
        var d = a.offsetParent;
        if (d) {
            b += d.offsetLeft;
            c += d.offsetTop;
            return this.sumLeftTop(d, b, c)
        } else {
            return [b, c]
        }
    },
    showMenu: function(b, i) {
        //流程右击
        this.tempContextId = i;
        b = b || window.event;
        if (!b.pageX) {
            b.pageX = b.clientX
        }
        if (!b.pageY) {
            b.pageY = b.clientY
        }
        var h = b.pageX,
        f = b.pageY,
        d = com.xjwgraph.Global,
        c = d.lineTool.pathBody,
        j = d.baseTool.sumLeftTop(c);
        h = h - parseInt(j[0]) + parseInt(c.scrollLeft);
        f = f - parseInt(j[1]) + parseInt(c.scrollTop);
        var a = $id("isPixel"),
        e = a.style;
        if (i) {
            e.visibility = "hidden"
        } else {
            e.visibility = "visible"
        }
        var g = $id("pathBodyRightMenu");
        pathBodyRightMenuStyle = g.style;
        pathBodyRightMenuStyle.top = f + "px";
        pathBodyRightMenuStyle.left = h + "px";
        pathBodyRightMenuStyle.visibility = "visible";
        pathBodyRightMenuStyle.zIndex = com.xjwgraph.Global.modeTool.getNextIndex()
    },
    //浏览器判断
    checkBrowser: function() {
        var b = navigator.userAgent.toLowerCase();
        check = function(c) {
            return c.test(b)
        };
        var a = this;
        a.isOpera = check(/opera/);
        a.isIE = !a.isOpera && check(/msie/);
        a.isIE7 = a.isIE && check(/msie 7/);
        a.isIE8 = a.isIE && check(/msie 8/);
        a.isIE6 = a.isIE && !a.isIE7 && !a.isIE8;
        a.isChrome = check(/chrome/);
        a.isWebKit = check(/webkit/);
        a.isSafari = !a.isChrome && check(/safari/);
        a.isSafari2 = a.isSafari && check(/applewebkit\/4/);
        a.isSafari3 = a.isSafari && check(/version\/3/);
        a.isSafari4 = a.isSafari && check(/version\/4/);
        a.isGecko = !a.isWebKit && check(/gecko/);
        a.isGecko2 = a.isGecko && check(/rv:1\.8/);
        a.isGecko3 = a.isGecko && check(/rv:1\.9/);
    },
    getBrowserName: function() {
        var a = this;
        if (a.isIE) {
            if (a.isIE8) {
                return "IE8"
            } else if (a.isIE7) {
                return "IE7"
            } else if (a.isIE6) {
                return "IE6"
            } else {
                return "IE"
            }
        }
        if (a.isChrome) {
            return "CHROME"
        } else if (a.isWebKit) {
            return "WEBKIT"
        } else if (a.isOpera) {
            return "OPERA"
        } else if (a.isGecko) {
            return "GECKO"
        } else if (a.isGecko2) {
            return "GECKO2"
        } else if (a.isGecko3) {
            return "GECKO3"
        }
        if (a.isSafari) {
            return "SAFARI"
        } else if (a.isSafari2) {
            return "SAFARI2"
        } else if (a.isSafari3) {
            return "SAFARI3"
        } else if (a.isSafari4) {
            return "SAFARI4"
        }
    },
    forEach: function(e, c) {
        var d = e.getKeys(),
        a = d.length;
        for (var b = a; b--;) {
            if (c) {
                c(d[b])
            }
        }
    },
    removeAll: function() {
        var a = this,
        b = com.xjwgraph.Global.baseTool;
        a.forEach(a.contextMap,
        function(c) {
            var d = $id(c);
            b.contextMap.remove(c);
            b.pathBody.removeChild(d)
        })
    },
    // 555
    clearContext: function() {
        var b = this;
        b.tempContextId = null;
        var a = [],
        f = [],
        c = 0,
        e = com.xjwgraph.Global.baseTool;
        b.forEach(b.contextMap,
        function(i) {
            var j = $id(i),
            h = j.style;
            h.borderColor = PathGlobal.clearBoderColor;
            h.filter = "alpha(opacity=10)";
            h.opacity = "0.1";
            var g = j.getAttribute("groups");
            if (g == "false" || !g) {
                a[c] = j;
                f[c++] = e.contextMap.get(i);
                e.contextMap.remove(i);
                e.pathBody.removeChild(j)
            }
        });
        if (a.length > 0) {
            var d = new com.xjwgraph.UndoRedoEvent(function() {
                var h = a.length;
                for (var g = h; g--;) {
                    var j = a[g];
                    e.contextMap.put(j.id, f[g]);
                    e.pathBody.appendChild(j)
                }
            },
            PathGlobal.clearContext);
            d.setRedo(function() {
                var h = a.length;
                for (var g = h; g--;) {
                    var j = a[g];
                    if (j && j.id && $id(j.id)) {
                        e.contextMap.remove(j.id);
                        e.pathBody.removeChild(j)
                    }
                }
            })
        }
    },
    clear: function() {
        PathGlobal.rightMenu = false;
        var j = $id("pathBodyRightMenu");
        j.style.visibility = "hidden";
        var f = $id("isPixel");
        f.style.visibility = "hidden";
        var k = this,
        d = k.areaDiv.style,
        i = parseInt(d.top),
        c = parseInt(d.left),
        m = parseInt(d.width),
        l = parseInt(d.height);
        if (d.visibility != "visible") {
            return
        }
        var r = document.createElement("div"),
        t = r.style;
        t.position = "absolute";
        t.fontSize = "0px";
        t.borderWidth = "1px";
        t.borderStyle = "solid";
        t.borderColor = PathGlobal.defaultColor;
        t.visibility = "visible";
        t.top = 0 + "px";
        t.left = 0 + "px";
        t.width = 0 + "px";
        t.height = 0 + "px";
        t.backgroundColor = PathGlobal.selectColor;
        t.filter = "alpha(opacity=20)";
        t.opacity = "0.2";
        var p = com.xjwgraph.Global,
        h = p.modeTool,
        o = p.lineTool,
        n = h.getNextIndex();
        t.zIndex = n;
        r.setAttribute("id", "contextDiv" + n);
        var b = new Map(),
        a = new Map(),
        u = 0,
        q = 0,
        e = com.xjwgraph.Global.baseTool;
        o.clear();
        o.forEach(function(w) {
            var F = $id(w),
            E = o.getPath(F),
            D = o.getPathArray(E),
            C = D.length,
            B = true,
            A = 0,
            z = 0;
            for (var v = C; v--;) {
                var y = true;
                if (v % 2 == 1) {
                    A = i;
                    z = i + l
                } else {
                    A = c;
                    z = c + m;
                    y = false
                }
                if (! (D[v] >= A && D[v] <= z)) {
                    B = false;
                    break
                }
                if (y && (parseInt(t.top) > D[v] || parseInt(t.top) == 0)) {
                    t.top = D[v] - 2 + "px"
                }
                if (!y && (parseInt(t.left) > D[v] || parseInt(t.left) == 0)) {
                    t.left = D[v] - 2 + "px"
                }
                if (y && (parseInt(q) < parseInt(D[v]))) {
                    q = D[v]
                }
                if (!y && (parseInt(u) < parseInt(D[v]))) {
                    u = D[v]
                }
            }
            if (B) {
                var x = true;
                e.forEach(e.contextMap,
                function(G) {
                    var H = p.baseTool.contextMap.get(G).contextLineMap,
                    I = $id(G),
                    J = I.getAttribute("groups");
                    e.forEach(H,
                    function(K) {
                        if (K == F.id && (J == "true" || J)) {
                            x = false
                        }
                    })
                });
                stopEvent = true;
                if (x) {
                    o.show(F);
                    a.put(w, F)
                }
            }
        });
        h.forEach(function(C) {
            var z = $id(C),
            x = z.style,
            v = $id(C.replace("module", "backImg")),
            D = v.style,
            E = parseInt(x.top),
            w = parseInt(x.left),
            y = parseInt(D.width),
            B = parseInt(D.height),
            A = true;
            e.forEach(e.contextMap,
            function(F) {
                var H = p.baseTool.contextMap.get(F).contextModeMap,
                G = $id(F),
                I = G.getAttribute("groups");
                e.forEach(H,
                function(J) {
                    if (J == z.id && (I == "true" || I)) {
                        A = false
                    }
                })
            });
            if (A && E > i && w > c && w + y < c + m && E + B < i + l) {
                if (parseInt(t.top) > E || parseInt(t.top) == 0) {
                    t.top = E + "px"
                }
                if (parseInt(t.left) > w || parseInt(t.left) == 0) {
                    t.left = w + "px"
                }
                if (u < y + w) {
                    u = y + w
                }
                if (q < B + E) {
                    q = B + E
                }
                stopEvent = true;
                p.modeTool.showPointer(z);
                b.put(z.id, z)
            } else {
                p.modeTool.hiddPointer(z)
            }
        });
        k.clearContext();
        function g(w, v) {
            this.contextModeMap = w;
            this.contextLineMap = v
        }
        if ((b.size() + a.size()) > 1) {
            k.pathBody.appendChild(r);
            var g = new g(b, a);
            k.contextMap.put(r.id, g);
            k.tempContextId = r.id;
            t.width = (u - parseInt(t.left)) + "px";
            t.height = (q - parseInt(t.top)) + "px";
            var e = p.baseTool;
            var s = new com.xjwgraph.UndoRedoEvent(function() {
                if (r && r.id && $id(r.id)) {
                    e.pathBody.removeChild(r);
                    e.contextMap.remove(r.id)
                }
            },
            PathGlobal.baseClear);
            s.setRedo(function() {
                e.pathBody.appendChild(r);
                e.contextMap.put(r.id, g)
            });
            k.contextDivDrag(r, g)
        }
        d.top = 1 + "px";
        d.left = 1 + "px";
        d.width = 1 + "px";
        d.height = 1 + "px";
        d.visibility = "hidden"
    },
    contextDivDrag: function(d, b) {
        var i = d.style,
        c = com.xjwgraph.Global,
        h = c.baseTool,
        e = c.modeTool,
        f = c.lineTool,
        a = b.contextModeMap,
        g = b.contextLineMap;
        d.onclick = function() {
            i.borderColor = PathGlobal.defaultColor;
            i.filter = "alpha(opacity=30)";
            i.opacity = "0.3";
            h.forEach(h.contextMap,
            function(k) {
                if (k != d.id) {
                    var m = $id(k);
                    var l = m.style;
                    l.borderColor = PathGlobal.clearBoderColor;
                    l.filter = "alpha(opacity=10)";
                    l.opacity = "0.1"
                }
            })
        };
        d.oncontextmenu = function(k) {
            PathGlobal.rightMenu = true;
            h.showMenu(k, this.id);
            return false
        };
        d.ondragstart = function() {
            return false
        };
        d.onmousedown = function(m) {
            m = m || window.event;
            c.modeTool.clear();
            h.contextMoveAbale = true;
            i.borderColor = PathGlobal.defaultColor;
            i.filter = "alpha(opacity=20)";
            i.opacity = "0.2";
            i.visibility = "visible";
            h.forEach(h.contextMap,
            function(q) {
                if (q != d.id) {
                    var s = $id(q),
                    r = s.style;
                    r.borderColor = PathGlobal.clearBoderColor;
                    r.filter = "alpha(opacity=10)";
                    r.opacity = "0.1"
                }
            });
            var l = parseInt(i.top),
            p = parseInt(i.left);
            if (d.setCapture) {
                d.setCapture()
            } else {
                if (window.captureEvents) {
                    window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP)
                }
            }
            var k = m.layerX && m.layerX >= 0 ? m.layerX: m.offsetX,
            o = m.layerY && m.layerX >= 0 ? m.layerY: m.offsetY,
            l = parseInt(i.top),
            p = parseInt(i.left),
            n = document;
            n.onmousemove = function(u) {
                h.contextMoveAttempt = true;
                u = u || window.event;
                if (!u.pageX) {
                    u.pageX = u.clientX
                }
                if (!u.pageY) {
                    u.pageY = u.clientY
                }
                var r = u.pageX - k,
                q = u.pageY - o,
                t = com.xjwgraph.Global,
                s = t.lineTool.pathBody,
                v = t.baseTool.sumLeftTop(s);
                r = r - parseInt(v[0]) + parseInt(s.scrollLeft);
                q = q - parseInt(v[1]) + parseInt(s.scrollTop);
                i.left = r + "px";
                i.top = q + "px"
            };
            n.onmouseup = function(C) {
                C = C || window.event;
                if (!C.pageX) {
                    C.pageX = C.clientX
                }
                if (!C.pageY) {
                    C.pageY = C.clientY
                }
                var N = C.pageX - k,
                L = C.pageY - o,
                A = com.xjwgraph.Global,
                r = A.lineTool.pathBody,
                y = A.baseTool.sumLeftTop(r);
                N = N - parseInt(y[0]) + parseInt(r.scrollLeft);
                L = L - parseInt(y[1]) + parseInt(r.scrollTop);
                if (d.releaseCapture) {
                    d.releaseCapture()
                } else {
                    if (window.releaseEvents) {
                        window.releaseEvents(Event.MOUSEMOVE | Event.MOUSEUP)
                    }
                }
                n.onmousemove = null;
                n.onmouseup = null;
                if (h.contextMoveAttempt) {
                    var v = a.getKeys(),
                    w = v.length,
                    z = g.getKeys(),
                    J = z.length,
                    F = new Map(),
                    P = new Map();
                    for (var G = J; G--;) {
                        var O = z[G];
                        line = $id(O),
                        newLineLeft = N - p,
                        newLineTop = L - l,
                        path = f.getPath(line),
                        paths = f.getPathArray(path),
                        pathLength = paths.length,
                        lineMode = A.lineMap.get(line.id),
                        xBaseMode = lineMode.xBaseMode,
                        wBaseMode = lineMode.wBaseMode;
                        F.put(O, path);
                        for (var B = pathLength; B--;) {
                            if (B % 2 == 1) {
                                paths[B] = parseInt(paths[B]) + parseInt(newLineTop)
                            } else {
                                paths[B] = parseInt(paths[B]) + parseInt(newLineLeft)
                            }
                        }
                        var q = f.arrayToPath(paths);
                        P.put(O, q);
                        f.setPath(line, q);
                        f.setDragPoint(line);
                        if (wBaseMode && $id(wBaseMode.id) && !a.containsKey(wBaseMode.id)) {
                            f.removeAllLineAndMode(line, $id(wBaseMode.id))
                        }
                        if (xBaseMode && $id(xBaseMode.id) && !a.containsKey(xBaseMode.id)) {
                            f.removeAllLineAndMode(line, $id(xBaseMode.id))
                        }
                        f.clearLine(O)
                    }
                    function H(S, R) {
                        var Q = this;
                        Q.modeTop = S;
                        Q.modeLeft = R
                    }
                    var M = new Map(),
                    E = new Map();
                    for (var G = w; G--;) {
                        var x = $id(v[G]),
                        D = x.style,
                        u = N - p,
                        I = L - l;
                        M.put(x.id, new H(parseInt(D.top), parseInt(D.left)));
                        D.left = parseInt(D.left) + u + "px";
                        D.top = parseInt(D.top) + I + "px";
                        E.put(x.id, new H(parseInt(D.top), parseInt(D.left)));
                        e.changeBaseModeAndLine(x, true);
                    }
                    var K = new com.xjwgraph.UndoRedoEvent(function() {
                        i.top = l + "px";
                        i.left = p + "px";
                        for (var S = w; S--;) {
                            var U = $id(v[S]),
                            R = U.style,
                            T = M.get(U.id);
                            R.left = T.modeLeft + "px";
                            R.top = T.modeTop + "px";
                            e.showPointer(U);
                            e.changeBaseModeAndLine(U, true);
                        }
                        for (var S = J; S--;) {
                            var Q = z[S];
                            line = $id(Q),
                            oldLine = F.get(Q);
                            f.setPath(line, oldLine);
                            f.show(line);
                        }
                    },
                    PathGlobal.contextDivDrag);
                    var t = parseInt(i.top),
                    s = parseInt(i.left);
                    K.setRedo(function() {
                        i.top = t + "px";
                        i.left = s + "px";
                        for (var S = w; S--;) {
                            var U = $id(v[S]),
                            R = U.style,
                            T = E.get(U.id);
                            R.left = T.modeLeft + "px";
                            R.top = T.modeTop + "px";
                            e.showPointer(U);
                            e.changeBaseModeAndLine(U, true);
                        }
                        for (var S = J; S--;) {
                            var Q = z[S];
                            line = $id(Q),
                            q = P.get(Q);
                            f.setPath(line, q);
                            f.show(line);
                        }
                    })
                }
                h.contextMoveAttempt = false;
                h.contextMoveAbale = false
            }
        }
    },
    /**
    初始化绘图区域
    
    **/
    initPathBody: function(b) {
        /**
         * b为绘图区域DOM元素
         * e为鼠标左键选中区域div
         **/
        var b = $id(b.id),
        a = this,
        e = a.areaDiv,
        c = e.style;
        c.position = "absolute";
        c.width = 1 + "px";
        c.height = 1 + "px";
        c.fontSize = "0px";
        c.borderWidth = "1px";
        c.borderStyle = "solid";
        c.visibility = "hidden";
        c.backgroundColor = PathGlobal.selectColor;
        c.filter = "alpha(opacity=30)";
        c.opacity = "0.3";
        b.appendChild(e);
        // 选中元素后拖动
        b.ondragstart = function() {
            return false;
        };
        var d = com.xjwgraph.Global;
        b.onclick = function() {
            d.baseTool.clear();
        };
        b.ondblclick = function() {
            d.baseTool.clear();
        };
        /**
         * 
         */
        b.onmousedown = function(h) {
            c.borderColor = d.baseTool.checkColor;
            h = h || window.event;
            if (!PathGlobal.rightMenu) {
                d.baseTool.clear();
            }
            var f = h.clientX ? h.clientX: h.offsetX,
            j = h.clientY ? h.clientY: h.offsetY,
            g = d.lineTool.pathBody,
            i = d.baseTool.sumLeftTop(g);
            f = f - parseInt(i[0]) + parseInt(g.scrollLeft);
            j = j - parseInt(i[1]) + parseInt(g.scrollTop);
            c.left = f + "px";
            c.top = j + "px";
            if (d.modeTool.moveable == true || d.lineTool.moveable == true || d.baseTool.contextMoveAbale == true) {} else {
                c.visibility = "visible"
            }
            b.onmousemove = function(p) {
                p = p || window.event;
                var l = p.clientX,
                k = p.clientY,
                n = d.lineTool.pathBody,
                q = d.baseTool.sumLeftTop(n);
                l = l - parseInt(q[0]) + parseInt(n.scrollLeft);
                k = k - parseInt(q[1]) + parseInt(n.scrollTop);
                var o = l - f,
                m = k - j;
                if (e && c.visibility == "visible") {
                    if (l >= f) {
                        c.width = o + "px"
                    }
                    if (k >= j) {
                        c.height = m + "px"
                    }
                    if (k < j) {
                        c.top = k + "px";
                        c.height = Math.abs(m) + "px"
                    }
                    if (l < f) {
                        c.left = l + "px";
                        c.width = Math.abs(o) + "px"
                    }
                }
            };
            b.onmouseup = function(k) {
                /**
                chenjw1985@gmail.com
                20130501
                元素节点拉线抬起鼠标
                【点击元素】
                **/
                if (e && c.visibility == "visible" && !PathGlobal.rightMenu) {
                    d.baseTool.clear()
                }
            }
        }
    },
    drag: function(b, c, a) {
        var b = b,
        c = c,
        d = b.src,
        e = com.xjwgraph.Global;
        /**
         * if(d.indexOf("start")>-1) d=d.replace("start","baseMode1");
         * if(d.indexOf("rectangle")>-1) d=d.replace("rectangle","baseMode2");
         * if(d.indexOf("diamond")>-1) d=d.replace("diamond","baseMode3");
         * if(d.indexOf("end")>-1) d=d.replace("end","baseMode4");
         */
        d=b.id;
        b.ondragstart = function() {
            return false
        };
        b.onmousedown = function(i) {
            i = i || window.event;
            var k = $id("moveBaseModeImg");
            k.setAttribute('data-class','app_'+d);
            k.setAttribute('class','app_'+d);
            k.className='app_'+d;
            k.src = PathGlobal.pngImg;
            var h = $id("moveBaseMode"),
            g = h.style;
            g.visibility = "visible";
            if (h.setCapture) {
                h.setCapture()
            } else {
                if (window.captureEvents) {
                    document.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP)
                }
            }
            var f = i.clientX ? i.clientX: i.offsetX,
            l = i.clientY ? i.clientY: i.offsetY;
            g.left = f + "px";
            g.top = l + "px";
            var j = document;
            j.onmousemove = function(o) {
                o = o || window.event;
                var n = o.clientX,
                m = o.clientY;
                g.left = n + "px";
                g.top = m + "px"
            };
            var that =this;
            j.onmouseup = function(q) { 
                /**
                chenjw1985@gmail.com
                20130501
                拖动元素到画布
                */
                q = q || window.event;
                if (h.releaseCapture) {
                    h.releaseCapture()
                } else {
                    if (window.releaseEvents) {
                        document.releaseEvents(Event.MOUSEMOVE | Event.MOUSEUP)
                    }
                }
                j.onmousemove = null;
                j.onmouseup = null;
                g.visibility = "hidden";
                if (!q.pageX) {
                    q.pageX = q.clientX
                }
                if (!q.pageY) {
                    q.pageY = q.clientY
                }
                var o = q.pageX,
                m = q.pageY,
                p = e.lineTool.pathBody,
                r = e.baseTool.sumLeftTop(p);
                o = o - parseInt(r[0]) + parseInt(p.scrollLeft);
                m = m - parseInt(r[1]) + parseInt(p.scrollTop);
                var n = o >= 0 && m >= 0;
                /***************************************************************
                 * chenjw1985@gmail.com 20130503 只能有一个开始节点
                 **************************************************************/
                if (n) {
                    if (c) {
                        e.lineTool.create(o, m, a)
                    } else {
                        // 123
                        if(that.id == "start" && !!e.startModel){
                            jQuery.messager.alert('提示:','一个流程只能有一个开始节点！');
                            return;
                        }else if (that.id == "start" || that.id == 'end'){
                            //20130503 remove [ || that.id == 'end']
                            e.modeTool.create(m, o, d, that.id);
                        }else{
                            e.modeTool.create(m, o, d, false);
                        }
                    }
                }
                
            }
        }
    },
    initEndDiv: function(f, b) {
        var d = this;
        d.endDiv = document.createElement("div");
        var e = d.endDiv,
        g = e.style;
        g.left = f + "px";
        g.top = b + "px";
        g.width = "10px";
        g.height = "10px";
        g.fontSize = "10px";
        g.position = "absolute";
        d.pathBody.appendChild(e);
        var a = $id("topCross"),
        c = $id("leftCross");
        a.style.width = f + "px";
        c.style.height = b + "px"
    },
    isSVG: function() {
        return this.VGType() == "SVG"
    },
    VGType: function() {
        return window.SVGAngle || document.implementation.hasFeature("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1") ? "SVG": "VML"
    },
    isVML: function() {
        return this.VGType() == "VML"
    },
    // 123
    showProperty: function(b){
        var g = com.xjwgraph.Global,
        a = g.baseBase.prop,
        h = $id("prop"),
        e = g.clientTool;
        h.style.visibility = "";
        h.innerHTML = "";
        h.appendChild(this.addProItem(a));
        e.showDialog(b, PathGlobal.baseProTitle, g.baseBase);
    },
    /**
    20130720
    修改弹出表单，采用模版渲染
    purecolor@foxmail.com
    **/
    addProItem: function(a){
        var p = com.xjwgraph.Global.parameter,
            d = document.createElement("div");
        // 工作日历
        var fwc = [
                  {"key":"是","value":"1"},
                  {"key":"否","value":"0"}
             ],
            i=0,l=fwc.length,fwcv,fwck,
            _fwc=[];
        for(;i<l;i++){
        	fwcv = fwc[i]["value"];
        	fwck = fwc[i]["key"];
            if(fwcv==a["flow_work_calendar"]){
            	_fwc.push({"ov":fwcv,"osd":' selected="selected"',"on":fwck});
            }else{
            	_fwc.push({"ov":fwcv,"osd":'',"on":fwck});
            }
        };
        a._flow_work_calendar=_fwc;
        
       
        // 缺省查询表格
        var fdqf = p.flow_default_query_form,
            i=0,l=fdqf.length,fdqfv,fdqfk,
            _fdqf=[];
        for(;i<l;i++){
            fdqfv = fdqf[i]["id"];
            fdqfk = fdqf[i]["form_caption"];
            if(fdqfv==a["flow_default_query_form"]){
                _fdqf.push({"ov":fdqfv,"osd":' selected="selected"',"on":fdqfk});
            }else{
                _fdqf.push({"ov":fdqfv,"osd":'',"on":fdqfk});
            }
        };
        a._flow_default_query_form=_fdqf;

        //流程状态
        var fs = [
                {"key":"暂停","value":"0"},
                {"key":"调试","value":"1"},
                {"key":"运行","value":"2"}
            ],
            i=0,l=fs.length,fsv,fsk,
            _fs=[];
        for(;i<l;i++){
            fsv = fs[i]["value"];
            fsk = fs[i]["key"];
            if(fsv==a["flow_status"]){
                _fs.push({"ov":fsv,"osd":' selected="selected"',"on":fsk});
            }else{
                _fs.push({"ov":fsv,"osd":'',"on":fsk});
            }
        };
        a._flow_status=_fs;

        //标题
        var ftt = p.flow_title_table,
            i=0,l=ftt.length,fttv,fttk,
            _ftt=[];
        var titleIndex = -1;
        for(;i<l;i++){
            fttv = ftt[i]["id"];
            fttk = ftt[i]["vc_name"];
            if(fttv==a["flow_title_table"]){
                _ftt.push({"ov":fttv,"osd":' selected="selected"',"on":fttk});
                titleIndex = i;
            }else{
                _ftt.push({"ov":fttv,"osd":'',"on":fttk});
            }
        };
        a._flow_title_table=_ftt;
        //请选择标题字段
        if(titleIndex>=0){
            var ftc = p.flow_title_column[titleIndex],
                i=0,l=ftc.length,ftcv,ftck,
                _ftc=[];
            for(;i<l;i++){
                ftcv = ftc[i]["id"];
                ftck = ftc[i]["vc_name"];
                if(ftcv==a["flow_title_column"]){
                    _ftc.push({"ov":ftcv,"osd":' selected="selected"',"on":ftck});
                }else{
                    _ftc.push({"ov":ftcv,"osd":'',"on":ftck});
                }
            };
            a._flow_title_column=_ftc;
        }
        
        //console.log(a);
        d.innerHTML = juicer(tpl.canvas, {a:a});
        return d;
    },
    
     setOption : function(select){
        var a = com.xjwgraph.Global.baseBase.prop;
        var p = com.xjwgraph.Global.parameter;
        var column_select = $('#lineAttr_flow_title_column');
        var index = select.selectedIndex;
        document.getElementById('lineAttr_flow_title_column').options.length=1;
        if(index<=0) return;
        var titleColumn = p.flow_title_column[index-1],
        titleColumnValue,
        titleColumnKey;
        i=0,
        l=titleColumn.length;
        
        for(;i<l;i++){
            titleColumnValue = titleColumn[i]["id"];
            titleColumnKey = titleColumn[i]["vc_name"];
            if(titleColumnValue==a["flow_title_column"]){
//              column_select.append('<option value="'+titleColumnValue+'" selected>'+titleColumnKey+'</option>');
                $('<option value="'+titleColumnValue+'" selected>'+titleColumnKey+'</option>').appendTo("#lineAttr_flow_title_column");
            }else{
//              column_select.append('<option value="'+titleColumnValue+'">'+titleColumnKey+'</option>');
                $('<option value="'+titleColumnValue+'" >'+titleColumnKey+'</option>').appendTo("#lineAttr_flow_title_column");
            }
        }
    }
};
var LineTool = com.xjwgraph.LineTool = function(d) {
    var c = this;
    c.stepIndex = PathGlobal.lineDefStep;
    c.pathBody = d;
    var e = com.xjwgraph.Global;
    c.tool = e.baseTool;
    c.moveable = false;
    c.isSVG = c.tool.isSVG();
    c.isVML = c.tool.isVML();
    c.pathBody.oncontextmenu = function(g) {
        if (!PathGlobal.rightMenu) {
            PathGlobal.rightMenu = true;
            e.baseTool.showMenu(g)
        }
        return false
    };
    c.baseLineIdIndex = PathGlobal.lineDefIndex;
    if (c.isSVG) {
        var f = document;
        c.svgBody = f.createElementNS("http://www.w3.org/2000/svg", "svg");
        c.svgBody.setAttribute("id", "svgContext");
        c.svgBody.setAttribute("style", "position:absolute;z-index:0;");
        c.svgBody.setAttribute("height", this.pathBody.scrollHeight + "px");
        c.svgBody.setAttribute("width", this.pathBody.scrollWidth + "px");
        /**
         * 2013-4-25 右侧箭头
         */
        var b = f.createElementNS("http://www.w3.org/2000/svg", "marker");
        b.setAttribute("id", "arrow");
        b.setAttribute("viewBox", "0 0 20 26");
        b.setAttribute("refX", "0");
        b.setAttribute("refY", "13");
        b.setAttribute("markerUnits", "strokeWidth");
        b.setAttribute("markerWidth", "3");
        b.setAttribute("markerHeight", "7");
        b.setAttribute("orient", "auto");
        var a = f.createElementNS("http://www.w3.org/2000/svg", "path");
        a.setAttribute("d", "M 0 0 L 20 13 L 0 26 z");
        a.setAttribute("fill", PathGlobal.lineColor);
        a.setAttribute("stroke", PathGlobal.lineColor);
        b.appendChild(a);
        c.svgBody.appendChild(b);
        /**
         * 2013-4-25 左侧箭头
         */
        var lb = f.createElementNS("http://www.w3.org/2000/svg", "marker");
        lb.setAttribute("id", "larrow");
        lb.setAttribute("viewBox", "0 0 20 26");
        lb.setAttribute("refX", "0");
        lb.setAttribute("refY", "13");
        lb.setAttribute("markerUnits", "strokeWidth");
        lb.setAttribute("markerWidth", "3");
        lb.setAttribute("markerHeight", "7");
        lb.setAttribute("orient", "auto");
        var la = f.createElementNS("http://www.w3.org/2000/svg", "path");
        la.setAttribute("d", "M 0 13 L 20 0 L 20 26 z");
        la.setAttribute("fill", PathGlobal.lineColor);
        la.setAttribute("stroke", PathGlobal.lineColor);
        lb.appendChild(la);
        c.svgBody.appendChild(lb);
        c.pathBody.appendChild(c.svgBody);
    }
};
LineTool.prototype = {
    tempLine: null,
    removeAll: function() {
        var a = this;
        a.forEach(function(b) {
            a.removeNode(b)
        })
    },
    /**
    2013-4-25
    创建拖动节点
    **/
    createRect: function(f) {
        var a, e = document;
        if (this.isSVG) {
            var d = e.createElementNS("http://www.w3.org/2000/svg", "g");
            d.setAttribute("style", "cursor: pointer;");
            var c = e.createElementNS("http://www.w3.org/2000/svg", "rect");
            c.setAttribute("stroke", "black");
            c.setAttribute("id", f);
            c.setAttribute("fill", "#00FF00");
            c.setAttribute("shape-rendering", "crispEdges");
            c.setAttribute("shapeRendering", "crispEdges");
            c.setAttribute("stroke-width", "1");
            c.setAttribute("strokeWidth", "1");
            c.setAttribute("x", "100");
            c.setAttribute("y", "100");
            c.setAttribute("width", "7");
            c.setAttribute("height", "7");
            c.style.visibility = "hidden";
            d.appendChild(c);
            a = d
        } else {
            if (this.isVML) {
                var c = document.createElement("v:rect");
                c.setAttribute("id", f);
                var b = c.style;
                b.width = "7px";
                b.height = "7px";
                b.position = "absolute";
                b.left = "100px";
                b.top = "100px";
                b.cursor = "pointer";
                b.visibility = "hidden";
                c.fillcolor = "#00FF00";
                c.stroked = "black";
                a = c
            }
        }
        return a
    },
    removeNode: function(a) {
        var d = this,
        c = $id(a);
        var b = null;
        if (d.isVML && c) {
            b = d.pathBody;
            b.removeChild($id(a + "lineHead"));
            b.removeChild($id(a + "lineMiddle"));
            b.removeChild($id(a + "lineEnd"))
        } else {
            if (d.isSVG && c) {
                b = d.svgBody;
                b.removeChild($id(a + "lineHead").parentNode);
                b.removeChild($id(a + "lineMiddle").parentNode);
                b.removeChild($id(a + "lineEnd").parentNode)
            }
        }
        if (c) {
            b.removeChild(c);
            var e = com.xjwgraph.Global;
            e.lineMap.get(a).div.remove();
            e.lineMap.remove(a);
        }
    },
    formatPath: function(a) {
        if (this.isVML) {
            a = a.replaceAll(",", " "),
            a = a.replaceAll("e", "z"),
            a = a.replaceAll("l", "L ")
        } else {
            a = a.replaceAll(",NaN NaN", ""),
            a = a.replaceAll(",undefined undefined", "")
        }
        return a
    },
    getNextIndex: function() {
        var a = this;
        a.baseLineIdIndex += a.stepIndex;
        return a.baseLineIdIndex
    },
    getActiveLine: function() {
        // console.log("getActiveLine");
        var a;
        this.forEach(function(b) {
            var c = $id(b);
            if (com.xjwgraph.Global.lineTool.isActiveMode(c)) {
                activeMode = c
            }
        });
        return a
    },
    isActiveLine: function(a) {
        var b, c = com.xjwgraph.Global;
        if (c.lineTool.isVML) {
            b = (a.getAttribute("strokecolor") == PathGlobal.lineCheckColor)
        } else {
            if (c.lineTool.isSVG) {
                b = (a.getAttribute("style").indexOf(PathGlobal.lineCheckColor) > 0)
            }
        }
        return b
    },
    forEach: function(c) {
        var d = com.xjwgraph.Global.lineMap.getKeys(),
        b = d.length;
        for (var a = b; a--;) {
            if (c) {
                c(d[a])
            }
        }
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * 创建连接线
     * **/
    createBaseLine: function(b, h, a) {
        var e = this,
        d = null,
        g = document;
        var c = null;
        if (e.isSVG) {
            d = g.createElementNS("http://www.w3.org/2000/svg", "path");
            d.setAttribute("id", b);
            
            e.setPath(d, h);
            d.setAttribute('d', h);
            d.setAttribute('path', h);
            
            d.setAttribute("style", "cursor:pointer; fill:none; stroke:" + PathGlobal.lineColor + "; stroke-width:" + PathGlobal.strokeweight);
            d.setAttribute("stroke", "purple");
            d.setAttribute("marker-end", "url(#arrow)");
            /**
             * 20130425 判断是双向箭头
             */
            if(a >3){
                d.setAttribute("marker-start", "url(#larrow)");
                a -= 3;
            }
            d.setAttribute("brokenType", a);
            c = e.svgBody
        } else if (e.isVML) {
            d = g.createElement('<v:shape style="cursor:pointer;WIDTH:100;POSITION:absolute;HEIGHT:100" coordsize="100,100" filled="f" strokeweight="' + PathGlobal.strokeweight + 'px" strokecolor="' + PathGlobal.lineColor + '"></v:shape>');
            var f = g.createElement("<v:stroke EndArrow='classic'/>");
            d.appendChild(f);
            /**
             * 20130425 判断是双向箭头
             */
            if(a >3){
                var ff = g.createElement("<v:stroke StartArrow='classic'/>");
                d.appendChild(ff);
                a -= 3;
            }else{
                var ff = g.createElement("<v:stroke StartArrow='none'/>");
                d.appendChild(ff);
            }
            d.setAttribute("id", b);
            d.setAttribute("brokenType", a);
            e.setPath(d, h);
            
            c = e.pathBody
        }
        c.appendChild(d);
        e.setPath(d,h);
        c.appendChild(e.createRect(b + "lineHead"));
        c.appendChild(e.createRect(b + "lineMiddle"));
        c.appendChild(e.createRect(b + "lineEnd"));
        e.drag(d);
        return d
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * vml下path的值必须都是整数
     * **/
    setPath: function(a, c) {
        var b = this;
        if (b.isSVG) {
            c = c.replace("z", "")
        }
        c=c.replace(/\.[0-9]{0,1}/g,'');
        a.setAttribute("d", c);
        a.setAttribute("path", c);

        /***********************************************************************
         * 
         * 
         * chenjw1985@gmail.com 20130503 还原路径设置
         * 
         * 
         * 
         * var b = this; if (b.isSVG) { c = c.replace("z", "") } var cc =
         * com.xjwgraph.Global; var e = cc.lineMap.get(a.getAttribute("id"));
         * if(!!e && !!e.div){ var list = c.match(/\d+/g); var brokenType =
         * a.getAttribute("brokenType"); var length = list.length; var left =
         * list[length-2]-e.div.width()-8; var top =
         * list[length-1]-e.div.height(); if(brokenType == 2 && list[5]*1 >
         * list[7]*1){ top += 20; } if(brokenType == 3 && list[4]*1 >
         * list[6]*1){ left += e.div.width() + 12; }
         * e.div.css("top",top).css("left",left); } a.setAttribute("d", c);
         * a.setAttribute("path", c)
         **********************************************************************/
        
    },
    getMiddle: function(p, g, l) {
        p = p.replace("M", "");
        p = p.replace("m", "");
        p = p.replace("z", "");
        var o = p.split("L"),
        n = this,
        b = n.strTrim(o[1]),
        f,
        q,
        d;
        if (n.isSVG) {
            if (b.indexOf(",") > 0) {
                f = b.split(",");
                var e = n.strTrim(f[0]).split(" "),
                f = n.strTrim(f[1]).split(" "),
                c = parseInt(e[0]),
                m = parseInt(e[1]),
                a = parseInt(f[0]),
                k = parseInt(f[1]);
                d = [c, m, a, k];
                q = [parseInt(Math.abs(c + a) / 2), parseInt(Math.abs(k + m) / 2)]
            } else {
                var j = n.getLineHead(p),
                f = n.getLineEnd(p);
                q = [parseInt(Math.abs(j[0] + f[0]) / 2), parseInt(Math.abs(j[1] + f[1]) / 2)]
            }
        } else {
            if (n.isVML) {
                f = n.strTrim(b).split(" ");
                if (f.length > 2) {
                    var c = parseInt(n.strTrim(f[0])),
                    m = parseInt(n.strTrim(f[1])),
                    a = parseInt(n.strTrim(f[2])),
                    k = parseInt(n.strTrim(f[3]));
                    d = [c, m, a, k];
                    q = [parseInt(Math.abs(c + a) / 2), parseInt(Math.abs(k + m) / 2)]
                } else {
                    var j = n.getLineHead(p),
                    f = n.getLineEnd(p);
                    q = [parseInt(Math.abs(j[0] + f[0]) / 2), parseInt(Math.abs(j[1] + f[1]) / 2)]
                }
            }
        }
        if (l) {
            var i = $id(g + "lineMiddle"),
            h = i.style;
            i.setAttribute("x", q[0] - PathGlobal.dragPointDec);
            i.setAttribute("y", q[1] - PathGlobal.dragPointDec);
            if (n.isActiveLine($id(g))) {
                h.visibility = ""
            }
            h.left = q[0] - PathGlobal.dragPointDec + "px";
            h.top = q[1] - PathGlobal.dragPointDec + "px"
        }
        return d
    },
    getLineHead: function(d) {
        d = d.replace("M", "");
        d = d.replace("m", "");
        d = d.replace("z", "");
        var e = d.split("L"),
        a = this.strTrim(e[0]).split(" "),
        c = parseInt(a[0]),
        b = parseInt(a[1]);
        return [c, b]
    },
    getLineEnd: function(d) {
        d = d.replace("M", "");
        d = d.replace("m", "");
        d = d.replace("z", "");
        if (this.isSVG) {
            var e = d.split("L"),
            c = this,
            b = c.strTrim(e[1]),
            a;
            if (b.indexOf(",") > 0) {
                a = b.split(",");
                a = c.strTrim(a[a.length - 1]).split(" ")
            } else {
                a = b.split(" ");
                a = [a[a.length - 2], a[a.length - 1]]
            }
        } else {
            d = d.replace("L", " ");
            d = d.replace(",", " ");
            d = this.strTrim(d);
            var a = d.split(" ");
            a = [a[a.length - 2], a[a.length - 1]];
            return [parseInt(a[0]), parseInt(a[1])]
        }
        return [parseInt(a[0]), parseInt(a[1])]
    },
    brokenPath: function(i, a) {
        var h = this,
        g = h.getLineHead(i),
        c = g[0],
        f = g[1],
        d = h.getLineEnd(i),
        e = d[0],
        b = d[1];
        if (a == 2 || a == 5) {
            i = h.brokenVertical(c, f, e, b, i)
        } else {
            if (a == 3 || a == 6) {
                i = h.brokenCross(c, f, e, b, i)
            }
        }
        return i
    },
    broken: function(f, e, c, b, a, d) {
        if (a == 2 || a == 5) {
            d = this.brokenVertical(f, e, c, b, d)
        } else {
            if (a == 3 || a == 6) {
                d = this.brokenCross(f, e, c, b, d)
            }
        }
        return d
    },
    brokenVertical: function(g, f, b, a, e) {
        var h = this.getPathArray(e),
        c = h.length;
        if (PathGlobal.switchType || c < 5) {
            var d = a - f,
            e = "M " + g + " " + f + " L " + (g) + " " + (f + parseInt(d / 2)) + "," + (b) + " " + (a - parseInt(d / 2)) + "," + b + " " + a + " z"
        } else {
            h[0] = g;
            h[1] = f;
            h[2] = g;
            h[4] = b;
            h[5] = h[3];
            h[6] = b;
            h[7] = a;
            e = this.arrayToPath(h)
        }
        return e
    },
    brokenCross: function(g, f, b, a, e) {
        var h = this.getPathArray(e),
        c = h.length;
        if (PathGlobal.switchType || c < 5) {
            var d = b - g,
            e = "M " + g + " " + f + " L " + (g + parseInt(d / 2)) + " " + (f) + "," + (g + parseInt(d / 2)) + " " + (a) + "," + b + " " + a + " z"
        } else {
            h[0] = g;
            h[1] = f;
            h[3] = f;
            h[4] = h[2];
            h[5] = a;
            h[6] = b;
            h[7] = a;
            e = this.arrayToPath(h)
        }
        return e
    },
    getPathArray: function(a) {
        a = a.replace("M", ""),
        a = a.replace("m", ""),
        a = a.replace("z", ""),
        a = a.replace("L", ""),
        a = a.replace("  ", " "),
        a = a.replaceAll(",", " ");
        var b = this.strTrim(a).split(" "),
        b = b.join(","),
        b = b.replaceAll(",,", ",");
        return this.strTrim(b).split(",")
    },
    arrayToPath: function(a) {
        return smallPath = "M " + a[0] + " " + a[1] + "  L " + a[2] + " " + a[3] + "," + a[4] + " " + a[5] + "," + a[6] + " " + a[7]
    },
    create: function(d, g, b, bool) {
        var i = this,
        h = i.getNextIndex(),
        j = "M " + d + " " + g + " L " + (d + 100) + " " + g + " z";
        if (bool && b == 2) {
            j = "M " + d + " " + g + " L " + (d + 100) + " " + (g - 60) + " z";
            j = i.brokenPath(j, b);
        }else if (bool && b == 3) {
            j = "M " + d + " " + g + " L " + (d - 100) + " " + (g + 60) + " z";
            j = i.brokenPath(j, b);
        }else if (b != 1 && b != 4) {
            j = "M " + d + " " + g + " L " + (d + 100) + " " + (g + 60) + " z";
            j = i.brokenPath(j, b);
        }
        var k = i.createBaseLine("line" + h, j, b),
            e = new BuildLine();
        if(b>3)
            e.prop["line_arrow"] = 3;
        else
            e.prop["line_arrow"] = 2;
        e.id = "line" + h;
        var c = com.xjwgraph.Global;
        this.buildDiv(k , e);
        c.lineMap.put(e.id, e);
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            var n = k && k.id && $id(k.id);
            var m = null,
            l = k.getAttribute("id");
            if (i.isVML && n) {
                m = i.pathBody;
                m.removeChild($id(l + "lineHead"));
                m.removeChild($id(l + "lineMiddle"));
                m.removeChild($id(l + "lineEnd"))
            } else {
                if (i.isSVG && n) {
                    m = i.svgBody;
                    m.removeChild($id(l + "lineHead").parentNode);
                    m.removeChild($id(l + "lineMiddle").parentNode);
                    m.removeChild($id(l + "lineEnd").parentNode)
                }
            }
            if (n) {
                m.removeChild(k)
            }
            c.lineMap.remove(e.id);
        },
        PathGlobal.lineCreate);
        a.setRedo(function() {
            var m = null,
            l = k.getAttribute("id");
            if (i.isVML) {
                k.setAttribute("filled", "f");
                k.setAttribute("strokeweight", PathGlobal.strokeweight + "px");
                k.setAttribute("strokecolor", PathGlobal.lineColor);
                m = i.pathBody
            } else {
                if (i.isSVG) {
                    m = i.svgBody
                }
            }
            m.appendChild(k);
            m.appendChild(i.createRect(l + "lineHead"));
            m.appendChild(i.createRect(l + "lineMiddle"));
            m.appendChild(i.createRect(l + "lineEnd"));
            i.drag(k);
            c.lineMap.put(k.id, e);
        })
    },
    getPath: function(a) {
        var b = this,
        c = "";
        if (b.isSVG) {
            c = a.getAttribute("d")
        } else {
            c = a.path + ""
        }
        c = b.formatPath(c);
        return c
    },
    distancePoint: function(j, g, m) {
        var k = this,
        l = this.getPath(m),
        h = k.getLineHead(l),
        c = k.getLineEnd(l),
        b = parseInt(h[0]),
        i = parseInt(h[1]),
        a = parseInt(c[0]),
        f = parseInt(c[1]),
        e = Math.abs(Math.sqrt(Math.pow(b - j, 2) + Math.pow(i - g, 2))),
        d = Math.abs(Math.sqrt(Math.pow(a - j, 2) + Math.pow(f - g, 2)));
        return e <= d
    },
    strTrim: function(b) {
        b = b.replace(/^\s+/, "");
        for (var a = b.length - 1; a >= 0; a--) {
            if (/\S/.test(b.charAt(a))) {
                b = b.substring(0, a + 1);
                break
            }
        }
        return b
    },
    initScaling: function(a) {
        var b = this;
        b.forEach(function(d) {
            var e = $id(d),
            h = b.formatPath(b.getPath(e)),
            j = b.getPathArray(h),
            g = j.length;
            for (var f = g; f--;) {
                j[f] = parseInt(j[f] / a)
            }
            h = b.arrayToPath(j);
            b.setPath(e, h);
            b.setDragPoint(e);
        })
    },
    getEndPoint: function(a) {
        // console.log("getEndPoint");
        var b = a.split("L");
        b[1] = this.strTrim(b[1]);
        return b[1].split(" ")
    },
    getHeadPoint: function(a) {
        // console.log("getHeadPoint");
        var b = a.split("L");
        b[0] = this.strTrim(b[0]);
        return b[0].split(" ")
    },
    endPoint: function(b, h, f, a) {
        var c = this,
        e;
        if (a != 1) {
            var d = c.getLineHead(f);
            e = c.broken(parseInt(d[0]), parseInt(d[1]), b, h, a, f)
        } else {
            var g = f.split("L");
            g[0] = this.strTrim(g[0]);
            e = g[0] + " L " + b + " " + h + " z"
        }
        return e
    },
    headPoint: function(b, h, f, a) {
        var d = this,
        e;
        if (a != 1) {
            var c = d.getLineEnd(f);
            e = d.broken(b, h, parseInt(c[0]), parseInt(c[1]), a, f)
        } else {
            var g = f.split("L");
            g[1] = this.strTrim(g[1]);
            e = "M " + b + " " + h + " L " + g[1]
        }
        return e
    },
    vecMultiply: function(c, b, a) {
        return ((c.x - a.x) * (b.y - a.y) - (b.x - a.x) * (c.y - a.y))
    },
    poInTrigon: function(h, g, e, f) {
        var b = this,
        d = b.vecMultiply(h, f, g),
        c = b.vecMultiply(g, f, e),
        a = b.vecMultiply(e, f, h);
        if (d * c * a == 0) {
            return false
        }
        if ((d > 0 && c > 0 && a > 0) || (d < 0 && c < 0 && a < 0)) {
            return true
        }
        return false
    },
    getXYtoIndex:function(w,h,e){
        
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * 绑定元素的ｉｎｄｅｘ
     m:线
     c:div元素
     e:left
     d:top
     * **/
    buildModeAndPoint: function(m, c, e, d,startClassName) {
        // 123 
        /**
         * if(e >= parseInt(c.offsetLeft) + parseInt(c.offsetWidth)) e -= 5;
         * if(e <= parseInt(c.offsetLeft)) e += 5; if(d >= parseInt(c.offsetTop) +
         * parseInt(c.offsetHeight)) d -= 5; if(d <= parseInt(c.offsetTop)) d +=
         * 5;
         */
        /**
         * f,b  DIV
         * a DIV::left,top
         *  e:left
            d:top
         
        var topPoint=new Point();
        topPoint.x=parseInt(c.offsetLeft)+parseInt(c.offsetWidth)/2;
        topPoint.y=parseInt(c.offsetTop);
        
        var rightPoint=new Point();
        rightPoint.x=parseInt(c.offsetLeft)+parseInt(c.offsetWidth);
        rightPoint.y=parseInt(c.offsetTop)+parseInt(c.offsetHeight)/2;
        
        var bottomPoint=new Point();
        bottomPoint.x=parseInt(c.offsetLeft)+parseInt(c.offsetWidth)/2;
        bottomPoint.y=parseInt(c.offsetTop)+parseInt(c.offsetHeight);
        
        var leftPoint=new Point();
        leftPoint.x=parseInt(c.offsetLeft);
        leftPoint.y=parseInt(c.offsetTop)+parseInt(c.offsetHeight)/2;
        **/
        
        var cw = c.offsetWidth,
        ch = c.offsetHeight,
        cl = c.offsetLeft,
        ct = c.offsetTop,
        g = new Point();
        
        //point top-left
        a = new Point();
        a.x = cl;
        a.y = ct;
        //point top right
        var l = new Point();
        l.x = cl + cw;
        l.y = ct;
        //point middle
        var k = new Point();
        k.x = cl + cw / 2;
        k.y = ct + ch / 2;
        //event 
        var i = new Point();
        i.x = e;
        i.y = d;
        
        var j = this;
        
        if (j.poInTrigon(a, l, k, i)) {
            g.x = cw / 2;
            g.y = 0;
            g.index = PathGlobal.pointTypeUp;
        }
        l.x = cl;
        l.y = ct + ch;
        
        if (j.poInTrigon(a, l, k, i)) {
            g.x = 0;
            g.y = ch / 2;
            g.index = PathGlobal.pointTypeLeft;
        }
        a.x = cl + cw;
        a.y = ct + ch;
        
        if (j.poInTrigon(a, l, k, i)) {
            g.x = cw / 2;
            g.y = ch;
            g.index = PathGlobal.pointTypeDown;
        }
        l.x = cl + cw;
        l.y = ct;
        
        if (j.poInTrigon(a, l, k, i)) {
            g.x = cw;
            g.y = ch / 2;
            g.index = PathGlobal.pointTypeRight;
        }

        if(startClassName=='middle_right'){
            g.x = cw;
            g.y = ch / 2;
            g.index = PathGlobal.pointTypeRight;
        }else if(startClassName=='middle_left'){
            g.x = 0;
            g.y = ch / 2;
            g.index = PathGlobal.pointTypeLeft;
        }else if(startClassName=='bottom_middle'){
            g.x = cw / 2;
            g.y = ch;
            g.index = PathGlobal.pointTypeDown;
        }else if(startClassName=='top_middle'){
            g.x = cw / 2;
            g.y = 0;
            g.index = PathGlobal.pointTypeUp;
        }
        g.x = cl+ g.x;
        g.y = ct + g.y;
        //console.log('gg: ',g);
        return g
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * 绑定线的ｉｎｄｅｘ
     * **/
    buildLineAndMode: function(l, g, i, h, d, startClassName) {
        var k = this,
        j = k.buildModeAndPoint(l, g, i, h, startClassName),
        c = new BuildLine();
        c.index = j.index;
        c.id = l.id;
        k.pathLine(j.x, j.y, l, d);
        k.setDragPoint(l);
        var b = com.xjwgraph.Global,
        f = b.modeMap.get(g.id);
        c.type = d;
        f.lineMap.put(c.id + "-" + c.type, c);
        var e = b.lineMap.get(l.id);
        if (d) {
            e.xBaseMode = f;
            e.xIndex = j.index
        } else {
            e.wBaseMode = f;
            e.wIndex = j.index
        }
        b.lineMap.put(l.id, e);
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            if (d) {
                e.xBaseMode = null
            } else {
                e.wBaseMode = null
            }
            f.lineMap.remove(c.id + "-" + c.type);
            k.setDragPoint(l)
        },
        PathGlobal.buildLineAndMode);
        a.setRedo(function() {
            if (d) {
                e.xBaseMode = f
            } else {
                e.wBaseMode = f
            }
            f.lineMap.put(c.id + "-" + c.type, c);
            k.setDragPoint(l)
        })
    },
    removeAllLineAndMode: function(a, b) {
        this.removeBuildLineAndMode(a, b, true);
        this.removeBuildLineAndMode(a, b, false)
    },
    removeBuildLineAndMode: function(m, h, d) {
        var b = com.xjwgraph.Global,
        g = b.modeMap.get(h.id),
        j = g.lineMap,
        k = m.id + "-" + d;
        if (j.containsKey(k)) {
            var l = g.lineMap,
            f = l.get(k),
            c = null,
            i = null;
            l.remove(k);
            var e = b.lineMap.get(m.id);
            if (e && e.xBaseMode && e.xBaseMode.id == h.id) {
                c = e.xBaseMode,
                e.xBaseMode = null
            } else {
                if (e && e.wBaseMode && e.wBaseMode.id == h.id) {
                    e.wBaseMode = null;
                    i = e.wBaseMode
                }
            }
            var a = new com.xjwgraph.UndoRedoEvent(function() {
                l.put(k, f);
                e.xBaseMode = c;
                e.wBaseMode = i
            },
            PathGlobal.removeLineAndMode);
            a.setRedo(function() {
                l.remove(k);
                e.xBaseMode = null;
                e.wBaseMode = null
            })
        }
    },
    isMoveBaseMode: function(n, l, p, d) {
        // console.log("isMoveBaseMode");
        var a = com.xjwgraph.Global,
        m = a.modeMap.getKeys(),
        c = m.length,
        h = a.modeTool;
        for (var f = c; f--;) {
            var g = $id(m[f]),
            e = g.style,
            b = parseInt(e.left),
            j = g.offsetWidth + b,
            k = parseInt(e.top),
            o = g.offsetHeight + k;
            if (n > b && n < j && l > k && l < o) {
                h.hiddPointer(g);
                h.flip(a.modeTool.getModeIndex(g));
                break
            } else {
                h.hiddPointer(g)
            }
        }
    },
    isCoverBaseMode: function(n, l, r, d) {
        var a = com.xjwgraph.Global,
        m = a.modeMap.getKeys(),
        c = m.length,
        h = a.modeTool,
        p = this,
        /**
         * chenjw1985@gmail.com
         * 20130504
         * q is target div
         * 
         * **/
        q = a.modeTool.getActiveMode();
        if (q) {
            h.hiddPointer(q);
            h.flip(a.modeTool.getModeIndex(q));
            // core func code
            p.buildLineAndMode(r, q, n, l, d);
        }
        for (var f = c; f--;) {
            var g = $id(m[f]),
            e = g.style,
            b = parseInt(e.left),
            j = g.offsetWidth + b,
            k = parseInt(e.top),
            o = g.offsetHeight + k;
            if (q && q.id == g.id) {
                continue
            } else {
                h.hiddPointer(g);
                p.removeBuildLineAndMode(r, g, d)
            }
        }
    },
    pathLine: function(e, f, b, d, bool){
        var c = this,
        h = c.getPath(b),
        g,
        a = b.getAttribute("brokenType");
        if (d) {
            g = c.headPoint(parseInt(e), parseInt(f), h, a)
        } else {
            g = c.endPoint(parseInt(e), parseInt(f), h, a)
        }
        c.setPath(b, g);
    },
    clearLine: function(a) {
        var b = $id(a),
        e = com.xjwgraph.Global,
        g = e.lineTool;
        if (g.isVML) {
            b.setAttribute("strokecolor", PathGlobal.lineColor)
        } else {
            if (e.lineTool.isSVG) {
                b.setAttribute("style", "cursor:pointer; fill:none; stroke:" + PathGlobal.lineColor + "; stroke-width:2px;")
            }
        }
        var d = $id(a + "lineHead"),
        c = $id(a + "lineEnd"),
        f = $id(a + "lineMiddle");
        d.style.visibility = "hidden";
        c.style.visibility = "hidden";
        f.style.visibility = "hidden"
    },
    clear: function() {
        var b = com.xjwgraph.Global,
        c = b.lineTool;
        this.forEach(function(d) {
            c.clearLine(d)
        });
        PathGlobal.rightMenu = false;
        var a = $id("lineRightMenu");
        a.style.visibility = "hidden";
        c.tempLine = null;
    },
    setDragPoint: function(q) {
        var h = com.xjwgraph.Global.lineTool,
        p = h.formatPath(h.getPath(q)),
        g = q.getAttribute("id"),
        i = $id(g + "lineHead"),
        e = $id(g + "lineEnd"),
        l = $id(g + "lineMiddle"),
        m = h.getLineHead(p),
        d = h.getLineEnd(p),
        o = h.getMiddle(p, g, true),
        k = parseInt(m[0]),
        c = parseInt(m[1]),
        b = parseInt(d[0]),
        n = parseInt(d[1]),
        f = q.style;
        i.setAttribute("x", (b - PathGlobal.dragPointDec));
        i.setAttribute("y", (n - PathGlobal.dragPointDec));
        var j = i.style;
        j.left = (b - PathGlobal.dragPointDec) + "px";
        j.top = (n - PathGlobal.dragPointDec) + "px";
        e.setAttribute("x", (k - PathGlobal.dragPointDec));
        e.setAttribute("y", (c - PathGlobal.dragPointDec));
        var a = e.style;
        a.left = (k - PathGlobal.dragPointDec) + "px";
        a.top = (c - PathGlobal.dragPointDec) + "px";
        j.zIndex = 1;
        l.style.zIndex = 1;
        a.zIndex = 1;
        if (h.isActiveLine(q)) {
            j.visibility = "";
            a.visibility = ""
        }
    },
    showProperty: function(b) {
        var i = this.tempLine,
        c = com.xjwgraph.Global,
        d = c.lineMap,
        f = d.get(i.getAttribute("id")),
        a = f.prop,
        h = $id("prop"),
        g = document,
        e = c.clientTool;
        // 123
        
        h.style.visibility = "";
        h.innerHTML = "";
        h.appendChild(this.addProItem(a));
        e.showDialog(b, PathGlobal.lineProTitle, f)
    },
    showMenu: function(a, j) {
        //线条右击
        PathGlobal.rightMenu = true;
        var i = this;
        i.tempLine = j;
        a = a || window.event;
        if (!a.pageX) {
            a.pageX = a.clientX
        }
        if (!a.pageY) {
            a.pageY = a.clientY
        }
        var f = a.pageX,
        e = a.pageY,
        c = com.xjwgraph.Global,
        b = c.lineTool.pathBody,
        g = c.baseTool.sumLeftTop(b);
        f = f - parseInt(g[0]) + parseInt(b.scrollLeft);
        e = e - parseInt(g[1]) + parseInt(b.scrollTop);
        var d = $id("lineRightMenu"),
        h = d.style;
        h.top = e + "px";
        h.left = f + "px";
        h.visibility = "visible";
        h.zIndex = i.getNextIndex()
    },
    showId: function(a) {
        this.show($id(a))
    },
    show: function(a) {
        if (this.isVML) {
            a.setAttribute("strokecolor", PathGlobal.lineCheckColor)
        } else {
            if (this.isSVG) {
                a.setAttribute("style", "cursor:pointer;fill:none; stroke:" + PathGlobal.lineCheckColor + "; stroke-width:" + PathGlobal.strokeweight)
            }
        }
        this.setDragPoint(a)
    },
    drag: function(i) {
        var b = com.xjwgraph.Global,
        d = i.getAttribute("id"),
        f = $id(d + "lineHead"),
        c = $id(d + "lineEnd"),
        g = $id(d + "lineMiddle"),
        e = b.lineTool,
        a = e.pathBody,
        h = this;
        i.ondragstart = function() {
            return false
        };
        g.oncontextmenu = c.oncontextmenu = f.oncontextmenu = i.oncontextmenu = function(j) {
            e.showMenu(j, i);
            return false
        };
        c.onmousedown = f.onmousedown = i.onmousedown = function(k,bool) {
            /**
            chenjw1985@gmail.com
            20130504
            f:开始节点、c:结束节点、i:线条 点击事件
            **/
            k = k || window.event;
            b.modeTool.clear();
            b.lineTool.clear();
            e.moveable = true;
            var j = e.getPath(i),
            r = k.clientX ? k.clientX: k.offsetX,
            o = k.clientY ? k.clientY: k.offsetY,
            q = b.baseTool.sumLeftTop(a);
            r = r - parseInt(q[0]) + parseInt(a.scrollLeft);
            o = o - parseInt(q[1]) + parseInt(a.scrollTop);
            if (!k.pageX) {
                k.pageX = k.clientX
            }
            if (!k.pageY) {
                k.pageY = k.clientY
            }
            var n = k.clientX - r,
            m = k.clientY - o,
            l = e.distancePoint(r, o, i),
            s = document;
            h.show(i);
            s.onmousemove = function(v) {
                v = v || window.event;
                if (e.moveable) {
                    var t = v.clientX ? v.clientX: v.offsetX;
                    var u = v.clientY ? v.clientY: v.offsetY;
                    var w = b.baseTool.sumLeftTop(a);
                    t = t - parseInt(w[0]) + parseInt(a.scrollLeft);
                    u = u - parseInt(w[1]) + parseInt(a.scrollTop);
                    h.pathLine(t, u, i, l);
                    h.setDragPoint(i);
                }
            };
            s.onmouseup = function(G) {
                /**
                from to 1254
                chenjw1985@gmail.com
                20130501
                拉线后点击抬起
                s::document
                **/
                G = G || window.event;
                var I = G.clientX ? G.clientX: G.offsetX,
                x = G.clientY ? G.clientY: G.offsetY;
                var A = b.baseTool.sumLeftTop(a);
                I = I - parseInt(A[0]) + parseInt(a.scrollLeft);
                x = x - parseInt(A[1]) + parseInt(a.scrollTop);
                e.moveable = false;
                s.onmousemove = null;
                s.onmouseup = null;
                /***************************************************************
                 * chenjw1985@gmail.com 20130504 remove line.
                 **************************************************************/
                if(bool && !b.modeTool.getActiveMode()){
                    com.xjwgraph.Global.lineTool.removeNode(com.xjwgraph.Global.llll);
                    return;
                }


                var u = e.getPath(i);
                if (j != u) {
                    var y = e.getLineHead(j),
                    C = e.getLineEnd(j),
                    z = parseInt(y[0]),
                    H = parseInt(y[1]),
                    F = parseInt(C[0]),
                    w = parseInt(C[1]),
                    D = e.getLineHead(u),
                    t = e.getLineEnd(u),
                    E = parseInt(D[0]),
                    v = parseInt(D[1]),
                    B = parseInt(t[0]),
                    K = parseInt(t[1]);
                    if (z !== E || H !== v || F !== B || w !== K) {
                        /**
                         * chenjw1985@gmail.com
                         * 20130504
                         * bing mode's index
                         * **/
                        e.isCoverBaseMode(I, x, i, l);
                    }
                    /**
                    chenjw1985@gmail.com
                    j：坐标
                    i：元素
                    edit = "M " + list[0] + " " + list[1] + " L " + list[2] + " " + mid + "," + list[4] + " " + mid + "," + list[6] + " " + list[7];
                    ii.setPath(line, edit);
                    b.lineTool 
                    **/
                    var J = new com.xjwgraph.UndoRedoEvent(function() {
                        b.lineTool.setPath(i, j);
                        e.setDragPoint(i)
                    },
                    PathGlobal.lineMove);
                    J.setRedo(function() {
                        b.lineTool.setPath(i, u);
                        e.setDragPoint(i)
                    })
                }
            }
        };
        g.onmousedown = function(j) {
            c.onmousedown(j);
            document.onmousemove = function(l) {
                l = l || window.event;
                if (e.moveable) {
                    var m = l.clientX ? l.clientX: l.offsetX;
                    var s = l.clientY ? l.clientY: l.offsetY;
                    var r = b.baseTool.sumLeftTop(a);
                    m = m - parseInt(r[0]) + parseInt(a.scrollLeft);
                    s = s - parseInt(r[1]) + parseInt(a.scrollTop);
                    var u = e.formatPath(e.getPath(i));
                    var q = e.getLineHead(u),
                    n = e.getLineEnd(u),
                    p = $id(d + "lineMiddle");
                    var k = i.getAttribute("brokenType");
                    h.changeBrokenType(i, m, s);
                    if (k == 3) {
                        var t = "M " + q[0] + " " + q[1] + " L " + m + " " + q[1] + "," + m + " " + n[1] + "," + n[0] + " " + n[1] + " z";
                        e.setPath(i, t);
                        p.setAttribute("x", m - PathGlobal.dragPointDec);
                        p.style.left = m - PathGlobal.dragPointDec + "px"
                    } else {
                        if (k == 2) {
                            var t = "M " + q[0] + " " + q[1] + " L " + q[0] + " " + s + "," + n[0] + " " + s + "," + n[0] + " " + n[1] + " z";
                            e.setPath(i, t);
                            p.setAttribute("y", s - PathGlobal.dragPointDec);
                            p.style.top = s - PathGlobal.dragPointDec + "px"
                        }
                    }
                    e.setDragPoint(i);
                }
            }
        }
    },
    changeBrokenType: function(k, c, i) {
        var j = this.getPath(k),
        b = k.getAttribute("brokenType"),
        h = this.getLineHead(j),
        e = this.getLineEnd(j),
        d = (parseInt(c) > parseInt(h[0]) && parseInt(c) < parseInt(e[0]) || parseInt(c) < parseInt(h[0]) && parseInt(c) > parseInt(e[0])),
        a = (parseInt(i) > parseInt(h[1]) && parseInt(i) < parseInt(e[1]) || parseInt(i) < parseInt(h[1]) && parseInt(i) > parseInt(e[1])),
        g = (parseInt(c) < parseInt(h[0]) && parseInt(c) < parseInt(e[0]) || parseInt(c) > parseInt(h[0]) && parseInt(c) > parseInt(e[0])) && a,
        f = (parseInt(i) < parseInt(h[1]) && parseInt(i) < parseInt(e[1]) || parseInt(i) > parseInt(h[1]) && parseInt(i) > parseInt(e[1])) && d;
        if (g) {
            k.setAttribute("brokenType", 3)
        } else {
            if (f) {
                k.setAttribute("brokenType", 2)
            }
        }
    },
    /**
    20130720
    修改弹出表单，采用模版渲染
    purecolor@foxmail.com
    **/
    addProItem: function(a) {
        var p = com.xjwgraph.Global.parameter,
            d = document.createElement("div");
        // 路由类型
        var lrt = [
                {"key":"单人","value":"0"},
                {"key":"并行抢占式","value":"1"},
                {"key":"并行完全式","value":"2"},
                {"key":"并行结合式","value":"3"}
            ],
            i=0,l=lrt.length,lrtv,lrtk,
            _lrt=[];
        for(;i<l;i++){
            lrtv = lrt[i]["value"];
            lrtk = lrt[i]["key"];
            if(lrtv==a["line_route_type"]){
                _lrt.push({"ov":lrtv,"osd":' selected="selected"',"on":lrtk});
            }else{
                _lrt.push({"ov":lrtv,"osd":'',"on":lrtk});
            }
        };
        a._line_route_type=_lrt;

        // 箭头
        var lrt = [
                {"key":"正向","value":"0"},
                {"key":"反向","value":"1"}
            ],
            i=0,l=lrt.length,lrtv,lrtk,
            _lrt=[];
        for(;i<l;i++){
            lrtv = lrt[i]["value"];
            lrtk = lrt[i]["key"];
            if(lrtv==a["line_arrow"]){
                _lrt.push({"ov":lrtv,"osd":' selected="selected"',"on":lrtk});
            }else{
                _lrt.push({"ov":lrtv,"osd":'',"on":lrtk});
            }
        };
        a._line_route_type=_lrt;

        //console.log(a);
        d.innerHTML = juicer(tpl.line, {a:a});

        return d;
    },
    buildDiv: function(div, e){
        var list = $(div).attr("d").match(/\d+/g);
        var length = list.length;
        e.div = $("<div style=\"position:absolute;\"></div>").appendTo(this.pathBody);
        e.div.css("top",list[length-1]-e.div.width()).css("left",list[length-2]-e.div.height()-8);
    }
};
/**
 * 绘图区编辑还是生成？？？
 */
var ModeTool = com.xjwgraph.ModeTool = function(a) {
    var b = this;
    b.moveable = false;
    b.optionMode;
    b.baseModeIdIndex = PathGlobal.modeDefIndex;
    b.stepIndex = PathGlobal.modeDefStep;
    b.pathBody = a;
    // 有什么用？
    b.tempMode
};
ModeTool.prototype = {
    initScaling: function(a) {
        var b = this;
        b.forEach(function(l) {
            var g = $id(l),
            f = g.style,
            h = b.getModeIndex(g),
            i = $id("content" + h),
            j = $id("backImg" + h),
            m = i.style,
            k = j.style,
            e = parseInt(parseInt(g.offsetWidth) / a) + "px",
            d = parseInt(parseInt(g.offsetHeight) / a) + "px";
            f.top = parseInt(parseInt(f.top) / a) + "px";
            f.left = parseInt(parseInt(f.left) / a) + "px";
            m.width = e;
            m.height = d;
            k.width = e;
            k.height = d;
            f.width = e;
            f.height = d;
            b.showPointer(g);
        })
    },
    /**显示右击菜单**/
    showMenu: function(a, f) {
        //元素右击
        PathGlobal.rightMenu = true;
        var j = this;
        j.tempMode = f.parentNode;
        a = a || window.event;
        
        //console.log('a',a);
        //console.log('f',f);

        if (!a.pageX) {
            a.pageX = a.clientX
        }
        if (!a.pageY) {
            a.pageY = a.clientY
        }
        var e = a.pageX,
        d = a.pageY,
        c = com.xjwgraph.Global,
        b = c.lineTool.pathBody,
        h = c.baseTool.sumLeftTop(b);
        e = e - parseInt(h[0]) + parseInt(b.scrollLeft);
        d = d - parseInt(h[1]) + parseInt(b.scrollTop);
        
        if($('img',f).attr('class')=="app_baseMode6"){
        	 var g = $id("childProcesRightMenu");
        }else{
        	 var g = $id("rightMenu");
        }
       
        var i = g.style;
        i.top = d + "px";
        i.left = e + "px";
        i.visibility = "visible";
        i.zIndex = j.getNextIndex()
    },
    showProperty: function(e) {
        var d = com.xjwgraph.Global,
        c = d.modeMap,
        b = c.get(this.tempMode.id),
        h = b.prop,
        g = document,
        a = $id("prop"),
        f = d.clientTool;
        a.style.visibility = "";
        //console.log('prop',h);
        a.innerHTML = "";
        var t=$('img',$('#'+this.tempMode.id.replace('module','content'))).attr('data-class');
        a.appendChild(this.addProItem(h,t));
        f.showDialog(e, PathGlobal.modeProTitle, b)
    },
    showModeLct: function(e) {
        var d = com.xjwgraph.Global,
        c = d.modeMap,
        b = c.get(this.tempMode.id),
        h = b.prop,
        g = document,
        a = $id("prop");
    	seeLct(h.node_id);
    },
    /**
    purecolor@foxmail.com
    2013-12-07
    子流程节点属性
    
    showChildProperty:function(e){
        var d = com.xjwgraph.Global,
        c = d.modeMap,
        b = c.get(this.tempMode.id),
        h = b.prop,
        g = document,
        a = $id("prop"),
        f = d.clientTool;
        a.style.visibility = "";
        a.innerHTML = "";
        a.appendChild(this.addProItem(h));
        f.showDialog(e, PathGlobal.modeProTitle, b)
    },**/
    removeAll: function() {
        var a = this;
        a.forEach(function(b) {
            a.removeNode(b)
        })
    },
    removeNode: function(d) {
        var c = $id(d);
        if (c) {
            var b = com.xjwgraph.Global,
            a = b.lineTool.pathBody;
            this.hiddPointer(c);
            b.modeMap.remove(c.id);
            a.removeChild(c)
        }
    },
    getNextIndex: function() {
        var a = this;
        a.baseModeIdIndex += a.stepIndex;
        return a.baseModeIdIndex
    },
    setClass: function(b, a) {
        if (b) {
            b.setAttribute("class", a);
            b.setAttribute("className", a)
        }
    },
    createBaseMode: function(o, f, a, i, e, l) {
        var p = this,
        u = document,
        j = u.createElement("div"),
        t = u.createElement("div"),
        q = u.createElement("div"),
        s = u.createElement("img");
        j.appendChild(t);
        j.appendChild(q);
        q.appendChild(s);
        var n = j.style;
        n.top = o + "px";
        n.left = f + "px";
        n.zIndex = i;
        p.setClass(j, "module");
        p.setClass(t, "title");
        p.setClass(q, "content");
        j.id = "module" + i;
        t.id = "title" + i;
        q.id = "content" + i;
        s.id = "backImg" + i;
        s.style.width = e;
        /**
         * chenjw1985@gmail.com 20130503 初始元素
         */
        s.style.height = l;
        // s.src = a;
        // s.style.height = (a=='baseMode5'||a=='baseMode6')?'50px':l;
        s.setAttribute('class','app_'+a);
        s.setAttribute('data-class','app_'+a);
        s.className='app_'+a;
        s.src = PathGlobal.pngImg;
    
        var k = u.createElement("div"),
        d = u.createElement("div"),
        m = u.createElement("div"),
        c = u.createElement("div"),
        h = u.createElement("div"),
        b = u.createElement("div"),
        g = u.createElement("div"),
        r = u.createElement("div");
        p.setClass(k, "top_left");
        p.setClass(d, "top_middle");
        p.setClass(m, "top_right");
        p.setClass(c, "middle_left");
        p.setClass(h, "middle_right");
        p.setClass(b, "bottom_left");
        p.setClass(g, "bottom_middle");
        p.setClass(r, "bottom_right");
        k.id = "top_left" + i;
        d.id = "top_middle" + i;
        m.id = "top_right" + i;
        c.id = "middle_left" + i;
        h.id = "middle_right" + i;
        b.id = "bottom_left" + i;
        g.id = "bottom_middle" + i;
        r.id = "bottom_right" + i;
        j.appendChild(k);
        j.appendChild(d);
        j.appendChild(m);
        j.appendChild(c);
        j.appendChild(h);
        j.appendChild(b);
        j.appendChild(g);
        j.appendChild(r);
        return j
    },
    /**
    chenjw1985@gmail.com
    20130503
    创建节点元素
    **/
    create: function(f, c, j, bool) {
        var k = this,
        i = k.getNextIndex(),
        g = document,
        h = k.createBaseMode(f, c, j, i, "50px", "50px");
        k.pathBody.appendChild(h);
        //20131211
        //数据模型
        if(j=='baseMode6'){
            var d = new ChildMode();
        }else{
            var d = new BaseMode();
        }
        d.id = h.id;
        var b = com.xjwgraph.Global;
        // 123
        if(bool){
            b.startModel = "module" + i;
            d.type = bool;
            d.prop.node_initiate_tasks = "true";
        }
        b.modeMap.put(d.id, d);
        this.initEvent(i);
        var e = b.modeTool;
        e.flip(i);
        var a = new com.xjwgraph.UndoRedoEvent(function() {
            if ($id(h.id)) {
                if(d.type == "start"){
                    b.startModel = "";
                }
                e.pathBody.removeChild(h);
                b.modeMap.remove(h.id)
            }
        },
        PathGlobal.modeCreate);
        a.setRedo(function() {
            // 123
            if(d.type == "start"){
                b.startModel = d.id;
            }
            b.modeMap.put(d.id, d);
            e.pathBody.appendChild(h);
            e.showPointer(h);
            e.changeBaseModeAndLine(h, true);
        });
    },
    initEvent: function(a) {
        var b = com.xjwgraph.Global.modeTool;
        b.drag($id("content" + a));
        b.dragPoint($id("top_left" + a));
        b.dragPoint($id("top_middle" + a));
        b.dragPoint($id("top_right" + a));
        b.dragPoint($id("middle_left" + a));
        b.dragPoint($id("middle_right" + a));
        b.dragPoint($id("bottom_left" + a));
        b.dragPoint($id("bottom_middle" + a));
        b.dragPoint($id("bottom_right" + a));
        $id("content" + a).onclick = function() {
            //元素点击事件
            b.showPointer($id("module" + a))
        }
    },
    clear: function() {
        var b = com.xjwgraph.Global,
        a = b.modeTool;
        this.forEach(function(c) {
            a.hiddPointer($id(c))
        });
    },
    toTop: function() {
        var a = com.xjwgraph.Global.modeTool;
        this.forEach(function(d) {
            var c = $id(d),
            b = c.style;
            if (b.visibility == "visible") {
                b.zIndex = a.getNextIndex()
            } else {
                if (b.zIndex < 1) {
                    b.zIndex = 0
                } else {
                    b.zIndex = b.zIndex - 1
                }
            }
        })
    },
    toBottom: function() {
        this.forEach(function(c) {
            var b = $id(c),
            a = b.style;
            if (a.visibility == "visible") {
                a.zIndex = 0
            } else {
                if (a.zIndex == 0) {
                    a.zIndex = 1
                }
            }
        });
        stopEvent = true
    },
    forEach: function(d) {
        var a = com.xjwgraph.Global.modeMap.getKeys(),
        b = a.length;
        for (var c = b; c--;) {
            if (d) {
                d(a[c])
            }
        }
        stopEvent = true
    },
    hiddPointer: function(e) {
        var d = this.getModeIndex(e);
        $id("module" + d).style.visibility = "hidden";
        $id("top_left" + d).style.visibility = "hidden";
        $id("top_middle" + d).style.visibility = "hidden";
        $id("top_right" + d).style.visibility = "hidden";
        $id("middle_left" + d).style.visibility = "hidden";
        $id("middle_right" + d).style.visibility = "hidden";
        $id("bottom_left" + d).style.visibility = "hidden";
        $id("bottom_middle" + d).style.visibility = "hidden";
        $id("bottom_right" + d).style.visibility = "hidden";
        var c = $id("rightMenu");
        c.style.visibility = "hidden";
        var c1 = $id("childProcesRightMenu");
        c1.style.visibility = "hidden";
        PathGlobal.rightMenu = false;
        var a = $id("topCross"),
        b = $id("leftCross");
        a.style.visibility = "hidden";
        b.style.visibility = "hidden";
    },
    getModeIndex: function(b) {
        var a;
        if (b.className == "module") {
            a = 6;
        } else if (b.className == "content") {
            a = 7;
        }
        return b.id.substr(a)
    },
    showPointer: function(a) {
        this.showPointerId(this.getModeIndex(a))
    },
    showPointerId: function(i) {
        var d = $id("smallmodule" + i),
        p = com.xjwgraph.Global;
        if (d) {
            var a = d.style;
            a.borderWidth = "1px";
            a.borderStyle = "solid"
        }
        var e = $id("module" + i);
        e.style.visibility = "visible";
        var r = $id("top_left" + i),
        b = r.style,
        n = r.offsetHeight,
        f = r.offsetWidth,
        k = e.offsetHeight,
        c = e.offsetWidth;
        $id("title" + i).style.width = c + "px";
        b.top = -n / 2 + "px";
        b.left = -f / 2 + "px";
        b.visibility = "hidden";
        var g = $id("top_middle" + i).style;
        g.top = -n / 2 + "px";
        g.left = c / 2 - f / 2 + "px";
        g.visibility = "visible";
        var q = $id("top_right" + i).style;
        q.top = -n / 2 + "px";
        q.left = c - f / 2 + "px";
        q.visibility = "hidden";
        var l = $id("middle_left" + i).style;
        l.top = k / 2 - n / 2 + "px";
        l.left = -f / 2 + "px";
        l.visibility = "visible";
        var h = $id("middle_right" + i).style;
        h.top = k / 2 - n / 2 + "px";
        h.left = c - f / 2 + "px";
        h.visibility = "visible";
        var o = $id("bottom_left" + i).style;
        o.top = k - n / 2 + "px";
        o.left = -f / 2 + "px";
        o.visibility = "hidden";
        var s = $id("bottom_middle" + i).style;
        s.top = k - n / 2 + "px";
        s.left = c / 2 - f / 2 + "px";
        s.visibility = "visible";
        var m = $id("bottom_right" + i).style;
        m.top = k - n / 2 + "px";
        m.left = c - f / 2 + "px";
        m.visibility = "hidden";
        var j = $id("backImg" + i).style;
        j.width = (c - 2) + "px";
        j.height = (k - 2) + "px";
        j.top = "0px";
        j.left = "0px";
    },
    drag: function(f) {
        var b = f.parentNode,
        e = b.style,
        c = com.xjwgraph.Global,
        a = c.modeTool,
        d = c.lineTool,
        self = this;
        f.ondragstart = function() {
            return false
        };
        f.onclick = function() {
            d.clear();
            a.clear();
            a.showPointer(b)
        };
        f.ondblclick = function() {
            a.hiddPointer(b);
            a.flip(c.modeTool.getModeIndex(b))
        };
        f.onmousemove = function() {
            if (d.moveable) {
                a.showPointerId(c.modeTool.getModeIndex(b))
            }
        };
        f.onmouseout = function() {
            if (d.moveable) {
                a.hiddPointer(b)
            }
        };
        f.oncontextmenu = function(g) {
            //右击元素
            a.showMenu(g, f);
            return false
        };
        f.onmousedown = function(j) {
            d.clear();
            a.clear();
            a.isModeCross(b);
            a.moveable = true;
            j = j || window.event;
            a.showPointer(b);
            if (b.setCapture) {
                b.setCapture()
            } else {
                if (window.captureEvents) {
                    window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP)
                }
            }
            var h = j.layerX && j.layerX >= 0 ? j.layerX: j.offsetX,
            l = j.layerX && j.layerY >= 0 ? j.layerY: j.offsetY;
            stopEvent = true;
            var k = document,
            g = parseInt(b.offsetLeft),
            i = parseInt(b.offsetTop);
            k.onmousemove = function(p) {
                p = p || window.event;
                if (a.moveable) {
                    if (!p.pageX) {
                        p.pageX = p.clientX
                    }
                    if (!p.pageY) {
                        p.pageY = p.clientY
                    }
                    var n = p.pageX - h,
                    m = p.pageY - l,
                    o = c.lineTool.pathBody,
                    q = c.baseTool.sumLeftTop(o);
                    n = n - parseInt(q[0]) + parseInt(o.scrollLeft);
                    m = m - parseInt(q[1]) + parseInt(o.scrollTop);
                    e.left = n + "px";
                    e.top = m + "px";
                    a.isModeCross(b);
                    a.changeBaseModeAndLine(b, true);
                    a.showPointer(b);
                    // 333
                    // console.group("in");
                    // if(n<0)
                    // console.log(n);
                    // if(m<0)
                    // console.log(m);
                    if(parseInt(c.baseTool.pathBody.style.width) + c.baseTool.pathBody.scrollLeft - n <90){
                        c.baseTool.pathBody.scrollLeft += 10;
                        var ww = parseInt(c.baseTool.pathBody.style.width) + c.baseTool.pathBody.scrollLeft;
                        if(ww > 1600){
                            $id("svgContext").setAttribute("width", ww + 10 + "px");
                            $id("topCross").style.width = ww;
                        }
                    }
                    if(parseInt(c.baseTool.pathBody.style.height) + c.baseTool.pathBody.scrollTop - m <90){
                            c.baseTool.pathBody.scrollTop += 10;
                            var hh = parseInt(c.baseTool.pathBody.style.height) + c.baseTool.pathBody.scrollTop;
                            if(hh > 1000){
                                if(d.isSVG)
                                    $id("svgContext").setAttribute("height", hh + 10 + "px");
                                $id("leftCross").style.height = hh;
                            }
                    }
                    // console.groupEnd();
                };
            };
            /**
             * 上接点击元素或画布点击 1254line chenjw1985@gmail.com 20130501 点击元素抬起
             */
            k.onmouseup = function(n) {
                n = n || window.event;
                a.moveable = false;
                if (b.releaseCapture) {
                    b.releaseCapture()
                } else {
                    if (window.releaseEvents) {
                        window.releaseEvents(Event.MOUSEMOVE | Event.MOUSEUP)
                    }
                }
                k.onmousemove = null;
                k.onmouseup = null;
                var p = parseInt(b.offsetLeft),
                o = parseInt(b.offsetTop);
                if (g != p || i != o) {
                    var m = new com.xjwgraph.UndoRedoEvent(function() {
                        b.style.left = g + "px";
                        b.style.top = i + "px";
                        a.showPointer(b);
                        a.changeBaseModeAndLine(b, true);
                    },
                    PathGlobal.modeMove);
                    m.setRedo(function() {
                        e.left = p + "px";
                        e.top = o + "px";
                        a.showPointer(b);
                        a.changeBaseModeAndLine(b, true);
                    })
                }
            }
        }
    },
    findModeLine: function(g, b) {
        var a = com.xjwgraph.Global.modeMap.get(g.id),
        f = a.lineMap,
        h = f.getKeys(),
        d = h.length;
        for (var c = d; c--;) {
            var e = f.get(h[c]);
            if (b.id == e.id) {
                return e
            }
        }
        return null
    },
    /**
    20130425
    线条动态改变
    brokenType==1 直线
    brokenType==2 上下曲线
    brokenType==3 左右曲线
    直线
    **/
    changeLineType: function(k, f, b) {
        var i = this,
        gg = com.xjwgraph.Global,
        j = gg.lineMap.get(k.id),
        ii = gg.lineTool,
        a = j.xBaseMode,
        h = j.wBaseMode,
        d,
        e;
        if (a) {
            d = $id(a.id)
        }
        if (h) {
            e = $id(h.id)
        }
        if (a && a.id == f.id) {
            if(e && d){
                //左侧
                var c = i.findModeLine(e, k), line = $id(c.id), brokenType = line.getAttribute("brokenType"), list = line.getAttribute("d").match(/\d+/g), mid, diff, edit;
                if(brokenType == 2){
                    if(d.offsetTop+d.offsetHeight<e.offsetTop){
                        b.index = PathGlobal.pointTypeDown;
                        c.index = PathGlobal.pointTypeUp;
                        mid=e.offsetTop-(e.offsetTop-d.offsetTop-d.offsetHeight)/2;
                        // console.log('z1');
                    }else if(d.offsetTop+d.offsetHeight>e.offsetTop&&d.offsetTop<=e.offsetTop){
                        b.index = PathGlobal.pointTypeUp;
                        c.index = PathGlobal.pointTypeUp;
                        mid=d.offsetTop-15;
                        // console.log('z2');
                    }else if(d.offsetTop>=e.offsetTop&&d.offsetTop<e.offsetTop+e.offsetHeight){
                        b.index = PathGlobal.pointTypeDown;
                        c.index = PathGlobal.pointTypeDown;
                        mid=d.offsetTop+d.offsetHeight+15;
                        // console.log('z3');
                    }else{
                        b.index = PathGlobal.pointTypeUp;
                        c.index = PathGlobal.pointTypeDown;
                        mid=d.offsetTop-(d.offsetTop-e.offsetTop-e.offsetHeight)/2;
                        // console.log('z4');
                    }
                    edit = "M " + list[0] + " " + list[1] + " L " + list[2] + " " + mid + "," + list[4] + " " + mid + "," + list[6] + " " + list[7];
                    ii.setPath(line, edit);

                }else if (brokenType == 3){

                    if(d.offsetLeft+d.offsetWidth<e.offsetLeft){
                        //console.log('y1');
                        b.index = PathGlobal.pointTypeRight;
                        c.index = PathGlobal.pointTypeLeft;
                        mid=e.offsetLeft-(e.offsetLeft-d.offsetLeft-d.offsetWidth)/2;

                    }else if(d.offsetLeft+d.offsetWidth>e.offsetLeft&&d.offsetLeft<=e.offsetLeft){
                        //console.log('y2');
                        b.index = PathGlobal.pointTypeLeft;
                        c.index = PathGlobal.pointTypeLeft;
                        mid=d.offsetLeft-15;

                    }else if(d.offsetLeft>=e.offsetLeft&&d.offsetLeft<e.offsetLeft+e.offsetWidth){
                        //console.log('y3');
                        b.index = PathGlobal.pointTypeRight;
                        c.index = PathGlobal.pointTypeRight;
                        mid=d.offsetLeft+d.offsetWidth+15;

                    }else{
                        //console.log('y4');
                        b.index = PathGlobal.pointTypeLeft;
                        c.index = PathGlobal.pointTypeRight;
                        mid=d.offsetLeft-(d.offsetLeft-e.offsetLeft-e.offsetWidth)/2;

                    }
                    edit = "M " + list[0] + " " + list[1] + " L " + mid + " " + list[3] + "," + mid + " " + list[5] + "," + list[6] + " " + list[7];
                    ii.setPath(line, edit);

                }else{
                    // d = this
                    //console.log('11111111',list);

                    var dw=d.offsetLeft-e.offsetLeft,dh=d.offsetTop-e.offsetTop;
                    if(Math.abs(dw)>=Math.abs(dh)){
                        if(e.offsetLeft>d.offsetLeft){
                            //console.log('a');
                            b.index = PathGlobal.pointTypeRight;
                            c.index = PathGlobal.pointTypeLeft;
                        }else{
                            //console.log('b');
                            b.index = PathGlobal.pointTypeLeft;
                            c.index = PathGlobal.pointTypeRight;
                        }
                    }else{
                        if(e.offsetTop>d.offsetTop){
                            //console.log('c');
                            b.index = PathGlobal.pointTypeDown;
                            c.index = PathGlobal.pointTypeUp;
                        }else{
                            //console.log('d');
                            b.index = PathGlobal.pointTypeUp;
                            c.index = PathGlobal.pointTypeDown;
                        }
                    }

                    edit = "M " + list[0] + " " + list[1] + " L " + " " + list[2] + " " + list[3] ;
                    ii.setPath(line, edit);
                }

                this.changeBaseModeAndLine(e, false);
            }
        }
        if (h && h.id == f.id) {
            if (e && d) {
                //右侧
                var c = i.findModeLine(d, k), line = $id(c.id), brokenType = line.getAttribute("brokenType"), list = line.getAttribute("d").match(/\d+/g), mid, diff, edit;
                /**
                 * 20130425 忽略线条类别
                 * 
                 */
                if(brokenType == 2){
                    if(e.offsetTop+e.offsetHeight<d.offsetTop){

                        b.index = PathGlobal.pointTypeDown;
                        c.index = PathGlobal.pointTypeUp;
                        mid=d.offsetTop-(d.offsetTop-e.offsetTop-e.offsetHeight)/2;

                    }else if(e.offsetTop+e.offsetHeight>d.offsetTop&&e.offsetTop<=d.offsetTop){

                        b.index = PathGlobal.pointTypeUp;
                        c.index = PathGlobal.pointTypeUp;
                        mid=e.offsetTop-15;

                    }else if(e.offsetTop>=d.offsetTop&&e.offsetTop<d.offsetTop+d.offsetHeight){

                        b.index = PathGlobal.pointTypeDown;
                        c.index = PathGlobal.pointTypeDown;
                        mid=e.offsetTop+e.offsetHeight+15;

                    }else{

                        b.index = PathGlobal.pointTypeUp;
                        c.index = PathGlobal.pointTypeDown;
                        mid=e.offsetTop-(e.offsetTop-d.offsetTop-d.offsetHeight)/2;

                    }
                    edit = "M " + list[0] + " " + list[1] + " L " + list[2] + " " + mid + "," + list[4] + " " + mid + "," + list[6] + " " + list[7];
                    ii.setPath(line, edit);

                }else if (brokenType == 3){

                    if(e.offsetLeft+e.offsetWidth<d.offsetLeft){
                        
                        b.index = PathGlobal.pointTypeRight;
                        c.index = PathGlobal.pointTypeLeft;
                        mid=d.offsetLeft-(d.offsetLeft-e.offsetLeft-e.offsetWidth)/2;

                    }else if(e.offsetLeft+e.offsetWidth>d.offsetLeft&&e.offsetLeft<=d.offsetLeft){
                        
                        b.index = PathGlobal.pointTypeLeft;
                        c.index = PathGlobal.pointTypeLeft;
                        mid=e.offsetLeft-15;

                    }else if(e.offsetLeft>=d.offsetLeft&&e.offsetLeft<d.offsetLeft+d.offsetWidth){
                        
                        b.index = PathGlobal.pointTypeRight;
                        c.index = PathGlobal.pointTypeRight;
                        mid=e.offsetLeft+e.offsetWidth+15;

                    }else{
                        
                        b.index = PathGlobal.pointTypeLeft;
                        c.index = PathGlobal.pointTypeRight;
                        mid=e.offsetLeft-(e.offsetLeft-d.offsetLeft-d.offsetWidth)/2;

                    }

                    edit = "M " + list[0] + " " + list[1] + " L " + mid + " " + list[3] + "," + mid + " " + list[5] + "," + list[6] + " " + list[7];
                    ii.setPath(line, edit);
                }else{
                    //e == this
                    //console.log('22222222',list);
                    var dw=e.offsetLeft-d.offsetLeft,dh=e.offsetTop-d.offsetTop;
                    if(Math.abs(dw)>=Math.abs(dh)){
                        if(d.offsetLeft>e.offsetLeft){
                            //console.log('a');
                            b.index = PathGlobal.pointTypeRight;
                            c.index = PathGlobal.pointTypeLeft;
                        }else{
                            //console.log('b');
                            b.index = PathGlobal.pointTypeLeft;
                            c.index = PathGlobal.pointTypeRight;
                        }
                    }else{
                        if(d.offsetTop>e.offsetTop){
                            //console.log('c');
                            b.index = PathGlobal.pointTypeDown;
                            c.index = PathGlobal.pointTypeUp;
                        }else{
                            //console.log('d');
                            b.index = PathGlobal.pointTypeUp;
                            c.index = PathGlobal.pointTypeDown;
                        }
                    }

                    edit = "M " + list[0] + " " + list[1] + " L " + " " + list[2] + " " + list[3] ;
                    ii.setPath(line, edit);
                }
                i.changeBaseModeAndLine(d, false);
            }
        }
        return b;
    },
    changeBaseModeAndLine: function(j, l) {
        var q = this,
        o = 0,
        m = 0,
        b = com.xjwgraph.Global,
        g = b.modeMap.get(j.id),
        n = g.lineMap,
        d = n.getKeys(),
        a = d.length;
        for (var e = a; e--;) {
            var c = n.get(d[e]),
            r = $id(c.id);
            if (r) {
                if (l) {
                    q.changeLineType(r, j, c);
                }
                var p = j.offsetWidth,
                f = j.offsetHeight;
                if (c.index == PathGlobal.pointTypeUp) {
                    o = 0;
                    m = p / 2
                } else if (c.index == PathGlobal.pointTypeLeft) {
                    o = f / 2;
                    m = 0
                } else if (c.index == PathGlobal.pointTypeDown) {
                    o = f;
                    m = p / 2
                } else if (c.index == PathGlobal.pointTypeRight) {
                    o = f / 2;
                    m = p
                }

                o += parseInt(j.offsetTop);
                m += parseInt(j.offsetLeft);
                var k = b.lineTool;
                k.pathLine(m, o, r, c.type);
                k.setDragPoint(r)
            }
        }
    },
    dragPoint: function(a) {
        var b = com.xjwgraph.Global;
        // 123
        a.ondragstart = function() {
            return false
        };
        a.onmousedown = function(q){
            // 123 取消选择框
            b.lineTool.moveable = true;
            q = q || window.event;
            if(!q.pageX){
                q.pageX = q.clientX;
            }
            if(!q.pageY){
                q.pageY = q.clientY;
            }
            var o = q.pageX,
            m = q.pageY,
            p = b.lineTool.pathBody,
            r = b.baseTool.sumLeftTop(p),
            index = b.lineTool.baseLineIdIndex + 2,
            bool = true;
            o = o - parseInt(r[0]) + parseInt(p.scrollLeft);
            m = m - parseInt(r[1]) + parseInt(p.scrollTop);
            var d = document;
            d.onmousemove = function(oo){
                oo = oo || window.event;
                if(!oo.pageX){
                    oo.pageX = oo.clientX;
                }
                if(!oo.pageY){
                    oo.pageY = oo.clientY;
                }
                if(bool){
                    if(Math.abs(oo.pageX-q.pageX)>10 || Math.abs(oo.pageY-q.pageY) > 10){
                        if(a.className == "middle_right")
                            b.lineTool.create(o, m, 3);
                        else if(a.className == "middle_left")
                            b.lineTool.create(o, m, 3, true);
                        else if(a.className == "bottom_middle")
                            b.lineTool.create(o, m, 2);
                        else if(a.className == "top_middle")
                            b.lineTool.create(o, m, 2, true);
                        /**
                         * chenjw1985@gmail.com
                         * 20130504
                         * 绑定线条到起始点
                         */
                        b.llll = "line" + index;
                        b.lineTool.buildLineAndMode($id("line" + index),b.modeTool.getActiveMode($(a).parent().attr("id")),o,m,true,a.className);
                        bool = false;
                    }
                }else{
                    $id("line"+index+"lineHead").onmousedown(true,true);
                }
            }
        }
    },
    isActiveMode: function(a) {
        return a.style.visibility == "visible"
    },
    getActiveMode: function(moduleid) {
        var b, a = com.xjwgraph.Global.modeTool;
        this.forEach(function(d) {
            var c = $id(d);
            // 123
            if (a.isActiveMode(c) && (!moduleid || moduleid == d)) {
                b = c
            }
        });
        return b
    },
    getSonNode: function(e, b) {
        for (var c = e.firstChild; c != null; c = c.nextSibling) {
            if (c.nodeType == 1) {
                var d = c.className;
                if (d == b) {
                    return c
                }
                if (d == "content" && b == "backImg") {
                    for (var a = c.firstChild; a != null; a = a.nextSibling) {
                        if (a.nodeType == 1) {
                            return a
                        }
                    }
                }
            }
        }
    },
    setIndex: function(e, d) {
        e.id = "module" + d;
        for (var b = e.firstChild; b != null; b = b.nextSibling) {
            if (b.nodeType == 1) {
                var c = b.className;
                b.id = c + d;
                if (c == "content") {
                    for (var a = b.firstChild; a != null; a = a.nextSibling) {
                        if (a.nodeType == 1) {
                            a.id = "backImg" + d;
                            break
                        }
                    }
                }
            }
        }
        return e
    },
    // 234
    copy: function(h) {
        var c = this,
        b = h.cloneNode(true),
        g = c.getNextIndex();
        c.setIndex(b, g);
        var e = b.style;
        e.left = parseInt(e.left) + PathGlobal.copyModeDec + "px";
        e.top = parseInt(e.top) + PathGlobal.copyModeDec + "px";
        //20131211
        //数据模型
        var a = new BaseMode();
        a.id = b.id;
        var d = com.xjwgraph.Global;
        d.modeMap.put(a.id, a);
        var f = d.lineTool;
        f.pathBody.appendChild(b);
        this.initEvent(g);
        return b
    },
    /**
     * chenjw1985@gmail.com
     * 20130503
     * 设置拖拽生成的元素大小
     * **/
    flip: function(g, a) {
        var c = com.xjwgraph.Global.modeMap.get("module" + g);
        if (c.isFilp) {
            return
        }
        c.isFilp = true;
        var f = $id("backImg" + g),
        // 234
        // e = f.height,
        // e = 80,
        e=f.offsetHeight,
        
        d = $id("content" + g),
        i = d.style;
        c.modeHeigh = e;
        // i.width = f.width + "px";
        i.width = "50px";
        i.fontSize = (e - parseInt(e * 0.15)) + "px";
        i.lineHeight = e + "px";
        i.height = e + "px";

        var b = $id(c.id),
        h = b.style;
        // h.width = f.width + "px";
        // h.width = "80px";
        h.width = f.offsetWidth+"px";
        
        h.height = e + "px";
        c.inc = c.modeHeigh / 10;
        this.doFlip(g, a)
    },
    doFlip: function(h, d) {
        var b = $id("backImg" + h);
        if (!b) {
            return
        }
        var e = com.xjwgraph.Global,
        c = b.height,
        g = e.modeMap.get("module" + h);
        c = c - g.inc;
        if (c < 1) {
            c = 1
        }
        if (c <= 1) {
            g.inc = -g.inc
        } else {
            if (c >= g.modeHeigh) {
                $id("backImg" + h).style.height = g.modeHeigh + "px";
                g.modeHeigh = 0;
                g.isFilp = false;
                g.inc = -g.inc;
                var a = $id("content" + h).style;
                a.width = 0 + "px";
                a.height = 0 + "px";
                a.lineHeight = 0 + "px";
                a.fontSize = 0 + "px";
                var f = $id("title" + h);
                if (d == "undefined" || !d) {} else {
                    f.innerHTML = d
                }
                this.showPointerId(h);
                return
            } else {
                $id("backImg" + h).style.height = c + "px"
            }
        }
        setTimeout(function() {
            e.modeTool.doFlip(h, d)
        },
        PathGlobal.pauseTime)
    },
    isModeCross: function(k) {
        var e = parseInt(k.offsetLeft),
        c = k.offsetWidth + e,
        a = parseInt(k.offsetWidth / 2) + e,
        h = parseInt(k.offsetTop),
        l = k.offsetHeight + h,
        g = parseInt(k.offsetHeight / 2) + h,
        n = $id("leftCross"),
        w = $id("topCross"),
        u = n.style,
        x = w.style,
        d = com.xjwgraph.Global.modeMap.getKeys(),
        s = false,
        j = false,
        m = d.length;
        for (var t = m; t--;) {
            var o = $id(d[t]);
            if (k.id == o.id) {
                continue
            }
            var f = parseInt(o.offsetLeft),
            q = o.offsetWidth + f,
            b = parseInt(o.offsetWidth / 2) + f,
            p = parseInt(o.offsetTop),
            r = o.offsetHeight + p,
            v = parseInt(o.offsetHeight / 2) + p;
            if (e == f || e == q) {
                n.style.left = e;
                j = true;
                n.style.visibility = "visible"
            }
            if (c == f || c == q) {
                u.left = c;
                j = true;
                u.visibility = "visible"
            }
            if (a == f || a == b) {
                u.left = a;
                j = true;
                u.visibility = "visible"
            }
            if (a == q) {
                u.left = a;
                j = true;
                u.visibility = "visible"
            }
            if (e == b || c == b) {
                u.left = b;
                j = true;
                u.visibility = "visible"
            }
            if (h == p || h == r) {
                x.top = h;
                s = true;
                x.visibility = "visible"
            }
            if (h == v || v == g || v == l) {
                x.top = v;
                s = true;
                x.visibility = "visible"
            }
            if (g == p || g == r) {
                x.top = g;
                s = true;
                x.visibility = "visible"
            }
            if (v == g) {
                x.top = v;
                s = true;
                x.visibility = "visible"
            }
            if (l == p || l == r) {
                x.top = l;
                s = true;
                x.visibility = "visible"
            }
        }
        if (!s) {
            x.visibility = "hidden"
        }
        if (!j) {
            u.visibility = "hidden"
        }
    },
    /**
    20130720
    修改弹出表单，采用模版渲染
    purecolor@foxmail.com
    **/
    addProItem: function(a,t) {
        /*20131207*/
        var p = com.xjwgraph.Global.parameter,
            d = document.createElement("div");
        if(t!='app_baseMode6'){
            //节点期限
            var t=["工作日","小时","天","周","月","年"]
	            , i=0,l=t.length,nrv,nrk
	            ,_nr=[];
	        for(;i<l;i++){
	            nrv = i;
	            nrk = t[i];
	            if(nrv==a["node_dead_line_unit"]&&a["node_dead_line_unit"]!=""){
	                _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
	            }else{
	                _nr.push({"ov":nrv,"osd":'',"on":nrk});
	            }
	        };
	        a._node_dead_line_unit=_nr;

            // 全局流程自定义状态
            var gpc = p.node_global_process_custom,
                i=0,l=gpc.length,gpcv,gpck,
                _gpc=[];
            for(;i<l;i++){
                gpcv = gpc[i]["vc_key"];
                gpck = gpc[i]["vc_value"];
                if(gpcv==a["node_global_process_custom"]){
                    _gpc.push({"ov":gpcv,"osd":' selected="selected"',"on":gpck});
                }else{
                    _gpc.push({"ov":gpcv,"osd":'',"on":gpck});
                }
            };
            a._node_global_process_custom=_gpc;

            //当前流程自定义状态
            var cpc = p.node_current_process_custom,
                i=0,l=cpc.length,cpcv,cpck,
                _cpc=[];
            for(;i<l;i++){
                cpcv = cpc[i]["vc_key"];
                cpck = cpc[i]["vc_value"];
                if(cpcv==a["node_current_process_custom"]){
                    _cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
                }else{
                    _cpc.push({"ov":cpcv,"osd":'',"on":cpck});
                }
            };
            a._node_current_process_custom=_cpc;

            // 默认表单
            var df = p.node_default_form,
                i=0,l=df.length,dfv,dfk,
                _df=[];
            for(;i<l;i++){
                dfv = df[i]["id"];
                dfk = df[i]["form_caption"];
                if(dfv==a["node_default_form"]){
                    _df.push({"ov":dfv,"osd":' selected="selected"',"on":dfk});
                }else{
                    _df.push({"ov":dfv,"osd":'',"on":dfk});
                }
            };
            a._node_default_form=_df;

            // 默认模板
            var dt = p.node_default_template,
                i=0,l=dt.length,dtv,dtk,
                _dt=[];
            for(;i<l;i++){
                dtv = dt[i]["id"];
                dtk = dt[i]["vc_cname"];
                if(dtv==a["node_default_template"]){
                    _dt.push({"ov":dtv,"osd":' selected="selected"',"on":dtk});
                }else{
                    _dt.push({"ov":dtv,"osd":'',"on":dtk});
                }
            };
            a._node_default_template=_dt;
            //路由类型
            var t=["单人","并行抢占式","并行完全式", "并行结合式","并行传阅式","并行办理式","串行传阅式"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_route_type"]&&a["node_route_type"]!=""){
                	_nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                	_nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_route_type=_nr;
            //办理状态
            var t=["待办","等办"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_work_state"]&&a["node_work_state"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_work_state=_nr;
            
            //是否交换
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["isexchange"]&&a["isexchange"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._isexchange=_nr;
            
          //是否回复
           var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isReply"]&&a["node_isReply"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isReply=_nr;
            
           
            $.ajax({
                url: '${ctx}/mobileTerminalInterface_getListWf.do?type=all',
                type: 'POST',
                cache: false,
                async: false,
                error: function(){
                    alert('AJAX调用错误');
                },
                success: function(msg){
                    if(msg=='-1'){
                        alert("数据库错误，请联系管理员!!!");
                    }else{ 
                    	var msgs = msg.split(";");
                        var cpc = msgs,
                        i=0,l=cpc.length,cpcv,cpck,
                        _cpc=[];
                        for(;i<l;i++){
                        	cpcv = cpc[i].split(",")[0];
                        	cpck = cpc[i].split(",")[1];
                        	if(cpcv==a["node_startJb"]){
                        		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
                        	}else{
                        		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
                        	}
                        };
                        a._node_startJb=_cpc;
                    }
                }
            });
            
            //是否合并子流程
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_child_merge"]&&a["node_child_merge"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_child_merge=_nr;
            

            //是否自循环
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_self_loop"]&&a["node_self_loop"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_self_loop=_nr;
            
            
            //是否延用
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[], tav=["1","0"];
            for(;i<l;i++){
                nrv = tav[i];
                nrk = t[i];
                if(nrv==a["node_form_continue"]&&a["node_form_continue"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_form_continue=_nr;
            
            //是否意见排序
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_comment_sort"]!=""&&nrv==a["node_comment_sort"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_comment_sort=_nr;
            
            //是否分批
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_send_again"]!=""&&nrv==a["node_send_again"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_send_again=_nr;
            
            //是否使用请阅意见框
            var t=["否","是"]
            , i=0,l=t.length,nrv,nrk
            ,_nr=[];
            for(;i<l;i++){
            	nrv = i;
            	nrk = t[i];
            	if(a["node_new_input"]!=""&&nrv==a["node_new_input"]){
            		_nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
            	}else{
            		_nr.push({"ov":nrv,"osd":'',"on":nrk});
            	}
            };
            a._node_new_input=_nr;
            
            //是否退回
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_send_back"]!=""&&nrv==a["node_send_back"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_send_back=_nr;
            
            //是否一键办理
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_one_key_handle"]!=""&&nrv==a["node_one_key_handle"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_one_key_handle=_nr;
            
            //是否一键办理不落签名
            var t=["否","是"]
            , i=0,l=t.length,nrv,nrk
            ,_nr=[];
            for(;i<l;i++){
            	nrv = i;
            	nrk = t[i];
            	if(a["node_auto_noname"]!=""&&nrv==a["node_auto_noname"]){
            		_nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
            	}else{
            		_nr.push({"ov":nrv,"osd":'',"on":nrk});
            	}
            };
            a._node_auto_noname=_nr;
            
            //是否过虑非本部门人员
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_skip_user"]!=""&&nrv==a["node_skip_user"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_skip_user=_nr;

            //是否自动办理
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_auto_send"]!=""&&nrv==a["node_auto_send"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_auto_send=_nr;
            
            //是否展示痕迹
            var t=["是","否"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_show_markbtn"]!=""&&nrv==a["node_show_markbtn"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_show_markbtn=_nr;
            
          //是否过滤下一步
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_skip_nextnodes"]!=""&&nrv==a["node_skip_nextnodes"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_skip_nextnodes=_nr;
            
            //下一节点是否自动办理
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_isautosend"]!=""&&nrv==a["node_isautosend"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isautosend=_nr;
            
            //是否为督办节点
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(a["node_issupervision"]!=""&&nrv==a["node_issupervision"]){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_issupervision=_nr;
            
            //是否作废
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_iszf"]&&a["node_iszf"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_iszf=_nr;
            
            //是否双屏幕展示
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_doubleScreen"]&&a["node_doubleScreen"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_doubleScreen=_nr;
            
            
            //是否作废
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_allowfair"]&&a["node_allowfair"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_allowfair=_nr;
            
            
          //是否套打
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_allowprint"]&&a["node_allowprint"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_allowprint = _nr;
            
          //是否选择正文模板
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_zwTemSel"]&&a["node_zwTemSel"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_zwTemSel = _nr;
            
            //是否发文(办文中走发文) 
            var t=["否","是"]
            , i=0,l=t.length,nrv,nrk
            ,_nr=[];
            for(;i<l;i++){
	            nrv = i;
	            nrk = t[i];
	            if(nrv==a["node_send_file"]&&a["node_send_file"]!=""){
	                _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
	            }else{
	                _nr.push({"ov":nrv,"osd":'',"on":nrk});
	            }
            };
            a._node_sendfile=_nr;
            
            //是否设置完成时限
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_iswcsx"]&&a["node_iswcsx"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_iswcsx=_nr;
            
            
            //是否设置办结提醒
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isEndRemind"]&&a["node_isEndRemind"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isEndRemind=_nr;
            
            
            //是否联合发文
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_islhfw"]&&a["node_islhfw"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_islhfw=_nr;
            
            //是否盖章
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isseal"]&&a["node_isseal"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isseal=_nr;
            

            //是否必须上传附件
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isUploadAttach"]&&a["node_isUploadAttach"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isUploadAttach=_nr;
            
          //允许上传附件
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_allowUpload"]&&a["node_allowUpload"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_allowUpload=_nr;
            
            //是否附件名称作为标题
            var t=["否","是"]
            , i=0,l=t.length,nrv,nrk
            ,_nr=[];
	        for(;i<l;i++){
	            nrv = i;
	            nrk = t[i];
	            if(nrv==a["node_isAttachAsTitle"]&&a["node_isAttachAsTitle"]!=""){
	                _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
	            }else{
	                _nr.push({"ov":nrv,"osd":'',"on":nrk});
	            }
	        };
	        a._node_isAttachAsTitle=_nr;
        
            //附件是否编辑
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isEdit"]&&a["node_isEdit"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isEdit=_nr;
            
            //是否隐藏
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isdisplay"]&&a["node_isdisplay"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isdisplay=_nr;
            
            
            //是否可以关注
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isfollow"]&&a["node_isfollow"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isfollow=_nr;
            
           //是否可以补发
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isreissue"]&&a["node_isreissue"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isreissue=_nr;
            
            //是否支持表单复制
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["form_copy"]&&a["form_copy"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._form_copy=_nr;
            
            
            //是否复签
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_iscountersign"]&&a["node_iscountersign"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_iscountersign=_nr;
            
            
          //是否支持强制拿回
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_forceback"]&&a["node_forceback"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_forceback=_nr;
            
          //是否在移动端办理
            var t=["是","否"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_doinmobile"]&&a["node_doinmobile"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_doinmobile=_nr;
            
            //是否10分钟自动关闭待办窗口
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isautoclosewin"]&&a["node_isautoclosewin"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isautoclosewin=_nr;
            
            //是否自动走完第一步
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isoverfirststep"]&&a["node_isoverfirststep"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isoverfirststep=_nr;

            
            // 节点人员
            var ns = p.node_staff,
                i=0,l=ns.length,nsv,nsk,
                _ns=[];
            for(;i<l;i++){
                nsv = ns[i]["id"];
                nsk = ns[i]["name"];
                if(nsv==a["node_staff"]){
                    _ns.push({"ov":nsv,"osd":' selected="selected"',"on":nsk});
                }else{
                    _ns.push({"ov":nsv,"osd":'',"on":nsk});
                }
            };
            a._node_staff=_ns;
            
            // 上一节点人员用户id
            var ns = p.node_lastStaff,
                i=0,l=ns.length,nsv,nsk,
                _ns=[];
            for(;i<l;i++){
                nsv = ns[i]["id"];
                nsk = ns[i]["name"];
                if(nsv==a["node_lastStaff"]){
                    _ns.push({"ov":nsv,"osd":' selected="selected"',"on":nsk});
                }else{
                    _ns.push({"ov":nsv,"osd":'',"on":nsk});
                }
            };
            a._node_lastStaff=_ns;
            
            //是否交换
            var t=["否","是"]
                , i=0,l=t.length,nrv,nrk
                ,_nr=[];
            for(;i<l;i++){
                nrv = i;
                nrk = t[i];
                if(nrv==a["node_isdefdep"]&&a["node_isdefdep"]!=""){
                    _nr.push({"ov":nrv,"osd":' selected="selected"',"on":nrk});
                }else{
                    _nr.push({"ov":nrv,"osd":'',"on":nrk});
                }
            };
            a._node_isdefdep=_nr;

            //请选择标题字段
            var ftc="";
            if(p.node_txnr_column.length>0){
            	ftc = p.node_txnr_column[0];
            	var i=0,l=ftc.length,ftcv,ftck,
            	_ftc=[];
                for(;i<l;i++){
                    ftcv = ftc[i]["id"];
                    ftck = ftc[i]["vc_name"];
                    if(ftcv==a["node_txnr_column"]){
                        _ftc.push({"ov":ftcv,"osd":' selected="selected"',"on":ftck});
                    }else{
                        _ftc.push({"ov":ftcv,"osd":'',"on":ftck});
                    }
                };
            }
            a._node_txnr_column=_ftc;
            
	        
	        
            // 节点固定人员
            var gdjdmes='新增';
            if(a["node_bdUser"])
                gdjdmes='编辑';
            a._node_bdUser={
                node_bdUser:a["node_bdUser"],
                gdjdmes:gdjdmes
            }
            // 绑定存储过程
            var pl = p.node_procedure_list,
                i=0,l=pl.length,plv,plk,
                _pl=[];
            for(;i<l;i++){
                plv = pl[i];
                plk = pl[i];
                if(plv==a["node_procedure_list"]){
                    _pl.push({"ov":plk,"osd":' selected="selected"',"on":plv});
                }else{
                    _pl.push({"ov":plk,"osd":'',"on":plv});
                }
            };
            a._node_procedure_list=_pl;
            _tpl=tpl.node;
            
            //发送至人员类型
            var empType=p.node_emptype
                 , i=0,l=empType.length,ltv,ltk
                 ,_lt=[];
             for(;i<l;i++){
             	ltv = i;
             	ltk = empType[i];
                 if(a["node_emptype"]!="" && ltk.value==a["node_emptype"]){
                 	_lt.push({"ov":ltk.value,"osd":' selected="selected"',"on":ltk.key});
                 }else{
                 	_lt.push({"ov":ltk.value,"osd":'',"on":ltk.key});
                 }
             };
             a._node_emptype=_lt;
        }else{
            //子流程
            //除去当前流程的所有流程
            $.ajax({
                url: '${ctx}/mobileTerminalInterface_getListWf.do',
                type: 'POST',
                cache: false,
                async: false,
                error: function(){
                    alert('AJAX调用错误');
                },
                success: function(msg){
                    if(msg=='-1'){
                        alert("数据库错误，请联系管理员!!!");
                    }else{ 
                    	var msgs = msg.split(";");
                        var cpc = msgs,
                        i=0,l=cpc.length,cpcv,cpck,
                        _cpc=[];
                        for(;i<l;i++){
                        	cpcv = cpc[i].split(",")[0];
                        	cpck = cpc[i].split(",")[1];
                        	if(cpcv==a["node_wfc_name"]){
                        		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
                        	}else{
                        		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
                        	}
                        };
                        a._node_wfc_name=_cpc;
                    }
                }
            });
            
            //获取节点名称
            $.ajax({
                url: '${ctx}/mobileTerminalInterface_getWfNodeList.do',
                type: 'POST',
                cache: false,
                async: false,
                error: function(){
                    alert('AJAX调用错误');
                },
                success: function(msg){
                    if(msg=='-1'){
                        alert("数据库错误，请联系管理员!!!");
                    }else{ 
                    	var msgs = msg.split(";");
                        var cpc = msgs,
                        i=0,l=cpc.length,cpcv,cpck,
                        _cpc=[];
                        for(;i<l;i++){
                        	cpcv = cpc[i].split(",")[0];
                        	cpck = cpc[i].split(",")[1];
                        	if(cpcv==a["node_wfc_nodeName"]){
                        		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
                        	}else{
                        		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
                        	}
                        };
                        a._node_wfc_nodeName=_cpc;
                    }
                }
            });
            
            
            
        	
            var cpc = [{"vc_key":"单例","vc_value":"0"},{"vc_key":"多例","vc_value":"1"}],
                i=0,l=cpc.length,cpcv,cpck,
                _cpc=[];
            for(;i<l;i++){
                cpcv = cpc[i]["vc_value"];
                cpck = cpc[i]["vc_key"];
                if(cpcv==a["node_wfc_ctype"]){
                    _cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
                }else{
                    _cpc.push({"ov":cpcv,"osd":'',"on":cpck});
                }
            };
            a._node_wfc_ctype=_cpc;

            var cpc = [{"vc_key":"同步串联","vc_value":"1"},{"vc_key":"异步并联","vc_value":"0"}],
                i=0,l=cpc.length,cpcv,cpck,
                _cpc=[];
            for(;i<l;i++){
                cpcv = cpc[i]["vc_value"];
                cpck = cpc[i]["vc_key"];
                if(cpcv==a["node_wfc_relation"]){
                    _cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
                }else{
                    _cpc.push({"ov":cpcv,"osd":'',"on":cpck});
                }
            };
            a._node_wfc_relation=_cpc;
            
            var cpc = [{"vc_key":"否","vc_value":"0"},{"vc_key":"是","vc_value":"1"}],
            	i=0,l=cpc.length,cpcv,cpck,
            		_cpc=[];
            for(;i<l;i++){
            	cpcv = cpc[i]["vc_value"];
            	cpck = cpc[i]["vc_key"];
            	if(cpcv==a["node_wfc_mainmerger"]){
            		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
            	}else{
            		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
            	}
            };
            a._node_wfc_mainmerger=_cpc;
            
            var cpc = [{"vc_key":"否","vc_value":"0"},{"vc_key":"是","vc_value":"1"}],
        	i=0,l=cpc.length,cpcv,cpck,
        		_cpc=[];
	        for(;i<l;i++){
	        	cpcv = cpc[i]["vc_value"];
	        	cpck = cpc[i]["vc_key"];
	        	if(cpcv==a["node_wfc_need_f_form"]){
	        		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
	        	}else{
	        		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
	        	}
	        };
	        a._node_wfc_need_f_form=_cpc;
            
            
            var cpc = [{"vc_key":"否","vc_value":"0"},{"vc_key":"是","vc_value":"1"}],
        	i=0,l=cpc.length,cpcv,cpck,
        		_cpc=[];
            for(;i<l;i++){
            	cpcv = cpc[i]["vc_value"];
            	cpck = cpc[i]["vc_key"];
            	if(cpcv==a["node_wfc_outparwf"]){
            		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
            	}else{
            		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
            	}
            };
            a._node_wfc_outparwf=_cpc;
            
            //返回待办
            var cpc = [{"vc_key":"否","vc_value":"0"},{"vc_key":"是","vc_value":"1"}],
        	i=0,l=cpc.length,cpcv,cpck,
        		_cpc=[];
            for(;i<l;i++){
            	cpcv = cpc[i]["vc_value"];
            	cpck = cpc[i]["vc_key"];
            	if(cpcv==a["node_return_pend"]){
            		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
            	}else{
            		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
            	}
            };
            a._node_return_pend=_cpc;
            
            //是否为发文流程
            var cpc = [{"vc_key":"否","vc_value":"0"},{"vc_key":"是","vc_value":"1"}],
        	i=0,l=cpc.length,cpcv,cpck,
        		_cpc=[];
            for(;i<l;i++){
            	cpcv = cpc[i]["vc_value"];
            	cpck = cpc[i]["vc_key"];
            	if(cpcv==a["node_isSend"]){
            		_cpc.push({"ov":cpcv,"osd":' selected="selected"',"on":cpck});
            	}else{
            		_cpc.push({"ov":cpcv,"osd":'',"on":cpck});
            	}
            };
            a._node_isSend=_cpc;
            
            _tpl=tpl.childnode;
            
            
        }
        //console.log(a);
        d.innerHTML = juicer(_tpl, {a:a});
        return d;
    }
};
/**
 * 
 */
var Map = com.xjwgraph.Map = function() {
    var a = this;
    a.map = new Object();
    a.length = 0
};
Map.prototype = {
    size: function() {
        return this.length
    },
    clear: function() {
        var a = this;
        a.map = new Object();
        a.length = 0
    },
    put: function(b, c) {
        var a = this;
        if (!a.map["_" + b]) {++a.length
        }
        a.map["_" + b] = c
    },
    putAll: function(a) {
        var f = a.getKeys(),
        d = f.length,
        c = this;
        for (var e = d; e--;) {
            var b = f[e];
            c.put(b, a.get(b))
        }
    },
    remove: function(b) {
        var a = this;
        if (a.map["_" + b]) {--a.length;
            return delete a.map["_" + b]
        } else {
            return false
        }
    },
    containsKey: function(a) {
        return this.map["_" + a] ? true: false
    },
    get: function(b) {
        var a = this;
        return a.map["_" + b] ? a.map["_" + b] : null
    },
    inspect: function() {
        var c = "",
        a = this;
        for (var b in a.map) {
            c += "\n" + b + "  Value:" + a.map[b]
        }
        return c
    },
    getKeys: function() {
        var d = new Array(),
        b = 0,
        a = this;
        for (var c in a.map) {
            d[b++] = c.replace("_", "")
        }
        return d
    }
};
// 123 是否需要补零
var GetTime = function(){
    var date = new Date();
    var time = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " "+ date.getHours() + ":";
    var mm = date.getMinutes();
    if(mm < 10){
        mm = "0" + mm;
    }
    return  time + mm;
}
// 123 创建时间，修改时间设置有问题
var BaseBase = com.xjwgraph.BaseBase = function(){
    this.prop = {
        "flow_name": "",
        "flow_create_time": "",
        "flow_modified_time": "",
        "flow_work_calendar": (new Date()).getFullYear()+"",
        "flow_initiate_tasks": "",
        "flow_default_query_form": "",
        "flow_status":"",
        "flow_title_table":"",
        "flow_title_column":"",
        "flow_initiate_titleNames":"",
        "flow_initiate_titleIds":"",
        "flow_id": ""
    }
}
var BaseMode = com.xjwgraph.BaseMode = function() {
    var a = this,
        g = com.xjwgraph.Global,
        p = g.parameter,
        bp = g.baseBase.prop;
    a.id;
    a.lineMap = new Map();
    a.prop = {
        "node_name": "",
        "node_create_time": GetTime(),
        "node_modified_time": "",
        "node_dead_line": "",
        "node_dead_line_unit": "0",
        "node_initiate_tasks": "true",
        "node_deadline_isworkday": "true",
        "node_global_process_custom": "",
        "node_current_process_custom": "",
        "node_default_form": bp.flow_default_query_form,
        "node_default_template": "",
        "node_redtape_template":"",
        "node_allow_consultation": "false",
        "node_allow_delegation": "false",
        "node_allow_cc": "false",
        "node_staff": "",
        "node_lastStaff": "",
        "node_isdefdep":"",
        "node_emptype":"",
        "node_bdUser": "",
        "node_procedure_list": "", 
        "node_id": Math.uuid(),
        "node_route_type": "",
        "node_work_state": "",
        "isexchange": "",
        "node_isReply": "",
        "node_startJb": "",
        "node_child_merge":"",
        "node_self_loop":"",
        "node_form_continue":"",
        "node_comment_sort":"",
        "node_send_again":"",
        "node_new_input":"",
        "node_send_back":"",
        "node_one_key_handle":"",
        "node_auto_noname":"",
        "node_skip_user":"",
        "node_child_nodeIds":"",
        "node_iszf":"",
        "node_sort_number":"",
        "node_doubleScreen":"",
         "node_allowfair":"",
         "node_iswcsx":"",
         "node_isEndRemind":"",
         "node_islhfw":"",
         "node_isseal":"",

         "node_isUploadAttach":"",
         "node_allowUpload":"",
         "node_isAttachAsTitle":"",
         "node_isEdit":"",
         
         "node_isdisplay":"",
         "node_isfollow":"",
         "node_isreissue":"",
         "form_copy":"",
         "node_iscountersign":"",
         "node_forceback":"",
         "node_doinmobile":"",
         "node_isoverfirststep":"",
         "node_tqtxsj_line": "",
         "node_txnr_txnrIds": "",
         "node_txnr_txnrNames": "",
         "node_allowprint":"",
         "node_zwTemSel":"",
         "node_send_file":"",
         "node_isautoclosewin":"",
         "node_auto_send_days":"",
         "node_auto_send":"",
         "node_show_markbtn":"",
         "node_skip_nextnodes":"",
         "node_isautosend":"",
         "node_issupervision":""
    }
};
var ChildMode = com.xjwgraph.ChildMode = function() {
    var a = this,
        g = com.xjwgraph.Global,
        p = g.parameter,
        bp = g.baseBase.prop;
    a.id;
    a.lineMap = new Map();
    a.prop = {
        "node_name": "",
        "node_create_time": GetTime(),
        "node_id": Math.uuid(),
        //子流程属性
        "node_wfc_name":"", 
        "node_wfc_nodeName":"",
        "node_wfc_pid":"", 
        "node_wfc_ctype":"", 
        "node_wfc_relation":"",
        "node_wfc_mainmerger":"",
        "node_wfc_outparwf":"",
        "node_return_pend":"",
        "node_isSend":"",
        "node_wfc_need_f_form":""
    }
};
var BuildLine = com.xjwgraph.BuildLine = function() {
    var a = this,p = com.xjwgraph.Global.parameter;
    a.id;
    a.index;
    a.xIndex;
    a.wIndex;
    a.type;
    a.xBaseMode;
    a.wBaseMode;
    a.prop = {
        "line_route_type": "0",
        "line_conditions": "",
        "line_choice_condition":"",
        "line_choice_rule":"",
        "line_arrow": "",
        "line_remark": "",
        "line_id": Math.uuid()
    }
};
var Point = com.xjwgraph.Point = function() {
    var a = this;
    a.x = 0;
    a.y = 0;
    a.index = 0
};
var KeyDownFactory = com.xjwgraph.KeyDownFactory = function() {};
KeyDownFactory.prototype = {
    removeNode: function() {
        var o = com.xjwgraph.Global,
        m = o.lineTool,
        g = o.baseTool,
        d = m.pathBody,
        f = m.svgBody,
        h = o.modeTool,
        r = [],
        n = [],
        q = 0,
        b = false,
        c = [],
        l = [],
        p = 0,
        t = false,
        a = null;
        h.forEach(function(w) {
            var v = $id(w);
            if (h.isActiveMode(v)) {
                n[q] = o.modeMap.get(v.id);
                r[q++] = v;
                a = o.modeMap.get(w).lineMap,
                baseLineKey = a.getKeys(),
                baseLineKeyLength = baseLineKey.length;
                for (var j = baseLineKeyLength; j--;) {
                    var u = a.get(baseLineKey[j]),
                    i = $id(u.id);
                    if (i) {
                        l[p] = com.xjwgraph.Global.lineMap.get(i.id);
                        c[p++] = i;
                        o.lineTool.removeNode(u.id)
                    }
                }
                o.modeTool.removeNode(w);
                if(o.startModel == w){
                    o.startModel = "";
                }
                b = true
            }
        });
        o.baseTool.clearContext();
        if (b) {
            var s = new com.xjwgraph.UndoRedoEvent(function() {
                var B = c.length,
                w = null;
                for (var y = B; y--;) {
                    var A = c[y],
                    u = A.getAttribute("id");
                    if (m.isVML) {
                        A.setAttribute("coordsize", "100,100");
                        A.setAttribute("filled", "f");
                        A.setAttribute("strokeweight", PathGlobal.strokeweight + "px");
                        A.setAttribute("strokecolor", PathGlobal.lineColor);
                        w = d
                    } else {
                        if (m.isSVG) {
                            w = f
                        }
                    }
                    w.appendChild(A);
                    w.appendChild(m.createRect(u + "lineHead"));
                    w.appendChild(m.createRect(u + "lineMiddle"));
                    w.appendChild(m.createRect(u + "lineEnd"));
                    m.drag(A);
                    o.lineMap.put(A.id, l[y]);
                }
                var x = r.length;
                for (var z = x; z--;) {
                    var v = r[z];
                    // 123
                    if(n[z].type == "start"){
                        o.startModel = n[z].id;
                    }
                    o.modeMap.put(v.id, n[z]);
                    d.appendChild(v);
                    h.showPointer(v);
                }
            },
            PathGlobal.removeMode);
            s.setRedo(function() {
                var x = c.length;
                for (var w = x; w--;) {
                    var v = c[w];
                    m.removeNode(v.id)
                }
                var u = r.length;
                for (var w = u; w--;) {
                    // 123
                    if(n[w].type == "start"){
                        o.startModel = "";
                    }
                    var j = r[w];
                    o.modeTool.removeNode(j.id)
                }
            })
        }
        m.forEach(function(i) {
            var j = $id(i);
            if (m.isActiveLine(j)) {
                l[p] = com.xjwgraph.Global.lineMap.get(j.id);
                c[p++] = j;
                m.removeNode(j.id);
                t = true
            }
        });
        if (t) {
            var e = new com.xjwgraph.UndoRedoEvent(function() {
                var x = c.length,
                u = null;
                for (var v = x; v--;) {
                    var w = c[v],
                    i = w.getAttribute("id");
                    if (m.isVML) {
                        w.setAttribute("coordsize", "100,100");
                        w.setAttribute("filled", "f");
                        w.setAttribute("strokeweight", PathGlobal.strokeweight + "px");
                        w.setAttribute("strokecolor", PathGlobal.lineColor);
                        u = d
                    } else {
                        if (m.isSVG) {
                            u = f
                        }
                    }
                    u.appendChild(w);
                    u.appendChild(m.createRect(i + "lineHead"));
                    u.appendChild(m.createRect(i + "lineMiddle"));
                    u.appendChild(m.createRect(i + "lineEnd"));
                    m.drag(w);
                    o.lineMap.put(w.id, l[v]);
                }
            },
            PathGlobal.remodeLine);
            e.setRedo(function() {
                var v = c.length;
                for (var u = v; u--;) {
                    var j = c[u];
                    m.removeNode(j.id)
                }
            })
        }
    }
};
var keyDownFactory = new KeyDownFactory();
function KeyDown(a) {
    a = a || window.event;
    if (a.keyCode == 46) {
        keyDownFactory.removeNode()
    }
}
com.xjwgraph.Global = {};
function $id(a) {
    return document.getElementById(a);
}
var stopEvent = false;
String.prototype.replaceAll = function stringReplaceAll(b, a) {
    raRegExp = new RegExp(b, "g");
    return this.replace(raRegExp, a)
};
var LineXML = com.xjwgraph.LineXML = function() {
    if(this["prop"]== null||this["prop"]==undefined){
        this["prop"]=new BuildLine().prop;
    }
};
LineXML.prototype = {
    setAttribute: function(a, b) {
        var lineTool = com.xjwgraph.Global.lineTool;
        if(",line_route_type,line_conditions,node_emptype,line_choice_condition,line_choice_rule,line_arrow,line_remark,line_id,".indexOf(","+a+",")>-1){
            if (this["prop"] == null) {
                this["prop"] = {}
            }
            this["prop"][a] = b;
        } else {
            this[a] = b;
        }
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * xml to html
     * **/
    view: function() {
        var g = this,
        a = com.xjwgraph.Global,
        f = a.lineTool,
        d = new BuildLine();
        var h = f.createBaseLine(g.id, g.d, g.brokenType*1);
        f.buildDiv(h,d);
        d.id = h.id;
        if (this["prop"]) {
            d.prop = this["prop"]
        }
        if(!!this["prop"]["line_remark"]){
            d.div.html(this["prop"]["line_remark"]);
        }
        
        var arrow = this["prop"]["line_arrow"];
        h.setAttribute("brokenType", g.brokenType);
        if(f.isSVG){
            h.style.strokeWidth = 2;
            if(arrow%2){
                h.setAttribute("marker-start","url(#larrow)");
            }
            if(arrow/2<1){
                h.removeAttribute("marker-end");
            }
        }else if(f.isVML){
            if(arrow%2){
                h.childNodes[0].setAttribute("StartArrow","classic");
            }
            if(arrow/2<1){
                h.childNodes[0].setAttribute("EndArrow","none");
            }
        }
        var e = a.modeTool,
        c = a.beanXml;
        if (g.xBaseMode) {
            var i = function() {
                var j = a.modeMap.get(g.xBaseMode);
                d.xBaseMode = j;
                d.xIndex = g.xIndex;
                var k = new BuildLine();
                k.id = g.id;
                k.type = true;
                k.index = g.xIndex;
                j.lineMap.put(g.id + "-true", k)
            };
            if (a.modeMap.get(g.xBaseMode)) {
                i()
            } else {
                c.delay[c.delayIndex++] = i
            }
        }
        if (g.wBaseMode) {
            var b = function() {
                var j = a.modeMap.get(g.wBaseMode);
                d.wBaseMode = j;
                d.wIndex = g.wIndex;
                var k = new BuildLine();
                k.id = g.id;
                k.type = false;
                k.index = g.wIndex;
                j.lineMap.put(g.id + "-false", k)
            };
            if (a.modeMap.get(g.wBaseMode)) {
                b()
            } else {
                c.delay[c.delayIndex++] = b
            }
        }
        a.lineMap.put(d.id, d);
        f.baseLineIdIndex = parseInt(g.id.substring(4)) + 1
    }
};
/**
 * 20131211
 * 子流程节点
 */
var ChildXML = com.xjwgraph.ChildXML = function() {
    var b = this,
    a = com.xjwgraph.Global.modeTool;
    b.modeDiv = a.createBaseMode(0, 0, "", 0, "50px", "50px");
    b.backImg = a.getSonNode(b.modeDiv, "backImg");
    b.title = a.getSonNode(b.modeDiv, "title");
    if(this["prop"]== null||this["prop"]==undefined){
        this["prop"]=new ChildMode().prop;
    }
};
ChildXML.prototype = {
        /**
         chenjw1985@gmail.com
         20130502
         解析xml到画布
         **/
    setAttribute: function(b, c) {
        
        var lineTool = com.xjwgraph.Global.lineTool;
        if (b == "backImgSrc") {
            this.backImg.setAttribute('data-class',c);
            this.backImg.setAttribute('class',c);
            this.backImg.className=c;
            this.backImg.src = PathGlobal.pngImg;
        } else if (b == "top") {
            this.modeDiv.style.top = c + "px"
        } else if (b == "left") {
            this.modeDiv.style.left = c + "px"
        } else if (b == "width") {
            /**
             chenjw1985@gmail.com
             20130503
             设置元素的宽度和高度为50px
             **/
            // this.modeDiv.style.width = c + "px";
            // this.backImg.style.width = c + "px"
            this.modeDiv.style.width = "50px";
            this.backImg.style.width = "50px"
        } else if (b == "height") {
            //this.modeDiv.style.height = c + "px";
            // this.backImg.style.height = c + "px";
            this.modeDiv.style.height = "50px";
            this.backImg.style.height = "50px";
        } else if (b == "id") {
            // 为什么不取原来的id
            com.xjwgraph.Global.modeTool.setIndex(this.modeDiv, c.substring(6))
        } else if (b == "title") {
            this.title.innerHTML = c
        } else if (b == "zIndex") {
            this.modeDiv.style.zIndex = c
        } else if (",node_id,node_name,node_wfc_node,node_wfc_name,node_wfc_nodeName,node_wfc_pid,node_wfc_ctype,node_wfc_relation,node_wfc_mainmerger,node_wfc_need_f_form,node_wfc_outparwf,node_return_pend,node_isSend,".indexOf(","+b+",")>-1) {
            if (this["prop"] == null) {
                this["prop"] = {}
            }
            this["prop"][b] = c;
        } else if(b == "class"){
            this.modeDiv.setAttribute("class", c);
            if(lineTool.isSVG){
                this.modeDiv.setAttribute("className", c);
            }
        }
    },
    /**
     * chenjw1985@gmail.com
     * 20131211
     * show xml to html
     * **/
    view: function() {
        var e = new ChildMode(),
        d = this.modeDiv,
        c = com.xjwgraph.Global,
        b = c.modeTool,
        lineTool = c.lineTool;
        b.pathBody.appendChild(d);
        var a = b.getModeIndex(d);
        e.id = d.id;
        if (this["prop"]) {
            e.prop = this["prop"];
        };
        if(!!this["prop"]["node_name"])
            $id("title" + d.id.match(/\d+/g)).innerHTML = this["prop"]["node_name"];
        c.modeMap.put(e.id, e);
        b.initEvent(a);
        b.showPointerId(a);
        b.hiddPointer(d);
        // 东西缺失，补回
        var style = d.childNodes[1].style;
        style.width = "0px";
        style.fontSize = "0px";
        style.lineHeight = "0px";
        style.height = "0px";
        b.baseModeIdIndex = parseInt(a) + 1
    }
};
/**
 * chenjw1985@gmail.com 20130503 修改元素为 50px
 */
var ModeXML = com.xjwgraph.ModeXML = function() {
    var b = this,
    a = com.xjwgraph.Global.modeTool;
    b.modeDiv = a.createBaseMode(0, 0, "", 0, "50px", "50px");
    b.backImg = a.getSonNode(b.modeDiv, "backImg");
    b.title = a.getSonNode(b.modeDiv, "title")
    if(this["prop"]== null||this["prop"]==undefined){
        this["prop"]=new BaseMode().prop;
    }
};
ModeXML.prototype = {
        /**
         chenjw1985@gmail.com
         20130502
         解析xml到画布
         **/
    setAttribute: function(b, c) {
        var lineTool = com.xjwgraph.Global.lineTool;
        if (b == "backImgSrc") {
            if(c.indexOf('/start.png')>-1||c.indexOf('/baseMode1.png')>-1){
                this.backImg.setAttribute('data-class','app_start');
                this.backImg.setAttribute('class','app_start');
                this.backImg.className='app_start';
            }else if(c.indexOf('/rectangle.png')>-1||c.indexOf('/baseMode2.png')>-1){
                this.backImg.setAttribute('data-class','app_baseMode5');
                this.backImg.setAttribute('class','app_baseMode5');
                this.backImg.className='app_baseMode5';
            }else if(c.indexOf('/diamond.png')>-1||c.indexOf('/baseMode3.png')>-1){
                this.backImg.setAttribute('data-class','app_baseMode6');
                this.backImg.setAttribute('class','app_baseMode6');
                this.backImg.className='app_baseMode6';
            }else if(c.indexOf('/end.png')>-1||c.indexOf('/baseMode4.png')>-1){
                this.backImg.setAttribute('data-class','app_end');
                this.backImg.setAttribute('class','app_end');
                this.backImg.className='app_end';
            }else{
                this.backImg.setAttribute('data-class',c);
                this.backImg.setAttribute('class',c);
                this.backImg.className=c;
            }
            this.backImg.src = PathGlobal.pngImg;
            
        } else if (b == "top") {
            this.modeDiv.style.top = c + "px"
        } else if (b == "left") {
            this.modeDiv.style.left = c + "px"
        } else if (b == "width") {
            /**
             chenjw1985@gmail.com
             20130503
             设置元素的宽度和高度为50px
             **/
            // this.modeDiv.style.width = c + "px";
            // this.backImg.style.width = c + "px"
            this.modeDiv.style.width = "50px";
            this.backImg.style.width = "50px"
        } else if (b == "height") {
            //this.modeDiv.style.height = c + "px";
            // this.backImg.style.height = c + "px";
            this.modeDiv.style.height = "50px";
            this.backImg.style.height = "50px";
        } else if (b == "id") {
            // 为什么不取原来的id
            com.xjwgraph.Global.modeTool.setIndex(this.modeDiv, c.substring(6))
        } else if (b == "title") {
            this.title.innerHTML = c
        } else if (b == "zIndex") {
            this.modeDiv.style.zIndex = c

        } else if (",node_name,node_create_time,node_modified_time,node_dead_line,node_dead_line_unit,node_initiate_tasks,node_deadline_isworkday,node_global_process_custom,node_current_process_custom,node_default_form,node_default_template,node_redtape_template,node_allow_consultation,node_allow_delegation,node_allow_cc,node_staff,node_lastStaff,node_isdefdep,node_bdUser,node_procedure_list,node_id,node_route_type,node_work_state,isexchange,node_isReply,node_startJb,node_child_merge,node_self_loop,node_form_continue,node_child_nodeIds,node_comment_sort,node_send_again,node_new_input,node_send_back,node_iszf,node_sort_number,node_doubleScreen,node_allowfair,node_allowprint,node_zwTemSel,node_send_file,node_iswcsx,node_isEndRemind,node_islhfw,node_isseal,node_isUploadAttach,node_allowUpload,node_isAttachAsTitle,node_isEdit,node_isdisplay,node_isfollow,node_isreissue,form_copy,node_tqtxsj_line,node_txnr_txnrIds,node_txnr_txnrNames,node_iscountersign,node_isoverfirststep,node_forceback,node_doinmobile,node_isautoclosewin,node_one_key_handle,node_auto_noname,node_skip_user,node_auto_send_days,node_auto_send,node_show_markbtn,node_skip_nextnodes,node_isautosend,node_issupervision,".indexOf(","+b+",")>-1) {

            if (this["prop"] == null) {
                this["prop"] = {}
            }
            this["prop"][b] = c;
        }else if(b == "nodetype" && (c == "start" || c=='end') ){
//            this.type = true;
                this.type = c;
        } else if(b == "class"){
            this.modeDiv.setAttribute("class", c);
            if(lineTool.isSVG){
                this.modeDiv.setAttribute("className", c);
            }
        }
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * show xml to html
     * **/
    view: function() {
        var e = new BaseMode(),
        d = this.modeDiv,
        c = com.xjwgraph.Global,
        b = c.modeTool,
        lineTool = c.lineTool;
        b.pathBody.appendChild(d);
        var a = b.getModeIndex(d);
        e.id = d.id;
        if(!!this.type) {
        /**
        chenjw1985@gmail.com
        20130817
        填个坑
        **/
//          e.type = "start"; 
            e.type = this.type;
            if(this.type=='start'){c.startModel = d.id;}
        };
        if (this["prop"]) {
            e.prop = this["prop"];
        };
        if(!!this["prop"]["node_name"])
            $id("title" + d.id.match(/\d+/g)).innerHTML = this["prop"]["node_name"];
        if(!!this["prop"]["node_sort_number"])
            $id("title" + d.id.match(/\d+/g)).innerHTML = $id("title" + d.id.match(/\d+/g)).innerHTML+"("+this["prop"]["node_sort_number"]+")";
        c.modeMap.put(e.id, e);
        b.initEvent(a);
        b.showPointerId(a);
        b.hiddPointer(d);
        // 东西缺失，补回
        var style = d.childNodes[1].style;
        style.width = "0px";
        style.fontSize = "0px";
        style.lineHeight = "0px";
        style.height = "0px";
        b.baseModeIdIndex = parseInt(a) + 1
    }
};
/**
 * XML
 */
var BeanXML = com.xjwgraph.BeanXML = function() {
    var a = this;
    a.delay = [];
    a.delayIndex = 0;
    a.doc = null;
    a.create();
    a.root = a.initBeanXML();
};
BeanXML.prototype = {
    create: function() {
        var a = this;
        a.doc = null;
        if (document.all) {
            var e = ["Msxml2.DOMDocument.6.0", "Msxml2.DOMDocument.5.0", "Msxml2.DOMDocument.4.0", "Msxml2.DOMDocument.3.0", "MSXML2.DOMDocument", "MSXML.DOMDocument", "Microsoft.XMLDOM"];
            var c = e.length;
            for (var b = c; b--;) {
                try {
                    a.doc = new ActiveXObject(e[b]);
                    break
                } catch(d) {
                    continue
                }
            }
        } else {
            a.doc = document.implementation.createDocument("", "", null);
        }
    },
    initBeanXML: function() {
        var b = this,
        c = b.doc.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"utf8\"");
        b.doc.appendChild(c);
        var a = b.doc.createElement("flow");
        b.doc.appendChild(a);
        return a
    },
    clearNode: function() {
        var a = this;
        if (a.root) {
            var d = a.root.childNodes,
            c = d.length;
            for (var b = c; b--;) {
                a.root.removeChild(d[b])
            }
        }
    },
    toXML: function() {
        // 123
        if(!com.xjwgraph.Global.startModel){
            jQuery.messager.alert('提示:','需要设置开始节点！');
            return;
        }
        var self = this,
        doc = self.doc;
        self.clearNode();
        var g = com.xjwgraph.Global,
        b = g.baseTool,
        bp = g.baseBase.prop,
        lineTool = g.lineTool;
        // 工作流属性
        //if(bp.flow_title_column==null || bp.flow_title_column==''){
        if(bp.flow_initiate_titleNames==null || bp.flow_initiate_titleNames==''){
            jQuery.messager.alert('提示:','需要设置流程标题！');
            return;
        }
        for(var obj in bp){
            self.appendProp(obj,bp[obj],self.root);
        }
        var idlist = [];
        // 节点属性
        b.forEach(g.modeMap,
        function(u){

            var o = $id(u),
            m = o.style,
            k = o.attributes,
            l = k.length;
            // 可设置的属性
            var mm = g.modeMap.get(u),
            mp = mm.prop;
            if(mp.node_wfc_pid!=undefined){
                flow = doc.createElement("flow_child");
            }else{
                flow = doc.createElement("flow_seq");
            };
            if(!!mm.type){
                self.appendProp("nodetype",mm.type,flow);
            }else{
                self.appendProp("nodetype","",flow);
            }
            for(var obj in mp){
                self.appendProp(obj,mp[obj],flow);
            }
            self.appendProp("class",k["class"].nodeValue,flow);

            var s = g.modeTool.getModeIndex(o);
            idlist.push(",module" + s + ",");
            self.appendProp("id","module" + s,flow);
            
            /**
             * chenjw1985@gmail.com 20130502 生成xml时设置原件背景
             */
            var q = $id("backImg" + s),
            //n = q.src; ::old
            n = q.getAttribute('data-class');
            self.appendProp("backImgSrc",n,flow);
            self.appendProp("top",parseInt(m.top),flow);
            self.appendProp("left",parseInt(m.left),flow);
            self.appendProp("zIndex",parseInt(m.zIndex),flow);
            // 待考虑，大小不会改变
            self.appendProp("width",parseInt(q.offsetWidth),flow);
            self.appendProp("height",parseInt(q.offsetHeight),flow);
            self.root.appendChild(flow);
        });

        var linebool = false;
        var modulestr = "";
        // 直线属性
        b.forEach(g.lineMap,
        function(r){
            var u = $id(r),
            n = u.attributes,
            q = u.style,
            flow = doc.createElement("flow_line");
            var gg = g.lineMap.get(u.id);
            if(!gg["wBaseMode"] || !gg["xBaseMode"]){
                linebool = true;
                return;
            }
            //20131212
            //增加线条两端节点类型
            self.appendProp("wBaseModeType", (gg["wBaseMode"].prop['node_wfc_pid']!=undefined?1:0),flow);
            self.appendProp("xBaseModeType", (gg["xBaseMode"].prop['node_wfc_pid']!=undefined?1:0),flow);

            modulestr += "," + gg["wBaseMode"]["id"] + "," + gg["xBaseMode"]["id"];
            
            for (var t in gg) {
                if (t === "prop" || t === "div") {
                    continue;
                }
                if (typeof(gg[t]) == "string" || typeof(gg[t]) == "number") {
                    self.appendProp(t,gg[t],flow);
                } else {
                    if (typeof(gg[t]) == "object") {
                        self.appendProp(t, gg[t] && gg[t].id ? gg[t].id: "",flow);
                    } else {
                        self.appendProp(t, gg[t] + " is unSupport",flow);
                    }
                }
            }
            if (gg && gg.prop) {
                var bp = gg.prop;
                for (var obj in bp) {
                    self.appendProp(obj, bp[obj],flow);
                }
            }

            if(lineTool.isSVG){
                if(n["marker-start"])
                    self.appendProp("marker-start", "true",flow);
                if(n["marker-end"])
                    self.appendProp("marker-end", "true",flow);
            }else if(lineTool.isVML){
                if(u.childNodes[0].getAttribute("StartArrow") == "classic")
                    self.appendProp("marker-start", "true",flow);
                if(u.childNodes[0].getAttribute("EndArrow") == "classic")
                    self.appendProp("marker-end", "true",flow);
            }

            self.appendProp("d", n["d"].nodeValue,flow);
            self.appendProp("brokenType", n["brokenType"].nodeValue,flow);
            self.root.appendChild(flow);
        });
        
        if(linebool){
            jQuery.messager.alert('提示:','请确保直线两端连接着节点！');
            return;
        }

        modulestr += ",";

        for(var i=0,l=idlist.length;i<l;i++){
            if(!(modulestr.indexOf(idlist[i])>-1)){
                jQuery.messager.alert('提示:','请确保节点都有直线连接！');
                return;
            }
        }
       
        var b= self.getTextXml(doc);
        $id("xml").value = b;
        $.post("mobileTerminalInterface_addWfDefineForImaging.do",$("#postForm").serialize(),function(a){
            alert('保存'+a);
            });
        // var formObj = $id("postForm");
        // formObj.target="postIframe";
        // formObj.submit();
        // self.viewTextXml(b);
    },
    loadXmlText: function() {
        if (document.all && window.ActiveXObject) {
            var a = this;
            return function(c) {
                var g = ["Msxml2.DOMDocument.6.0", "Msxml2.DOMDocument.5.0", "Msxml2.DOMDocument.4.0", "Msxml2.DOMDocument.3.0", "MSXML2.DOMDocument", "MSXML.DOMDocument", "Microsoft.XMLDOM"];
                var e = g.length;
                var b = null;
                for (var d = e; d--;) {
                    try {
                        b = new ActiveXObject(g[d]);
                        break
                    } catch(f) {
                        continue
                    }
                }
                b.async = "false";
                b.loadXML(c);
                return b
            }
        } else {
            return function(b) {
                return new DOMParser().parseFromString(b, "text/xml")
            }
        }
    } (),
    // 222
    viewTextXml: function(b) {
        b = b.replaceAll("<", "&lt").replaceAll(">", "&gt");
        var c = window.open("saveXml.html", "", ""),
        a = 0,
        d = [];
        d[a++] = "<html>";
        d[a++] = "<head>";
        d[a++] = '<link href="css/flowPath.css" type="text/css" rel="stylesheet" />';
        d[a++] = '<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />';
        d[a++] = "<title></title>";
        d[a++] = "</head>";
        d[a++] = "<body>";
        d[a++] = b;
        d[a++] = "</body></html>";
        c.document.write(d.join(""));
        c.document.close()
    },
    getTextXml: function(c) {
        var b = "";
        if (c) {
            b = c.xml;
            if (!b) {
                if (c.innerHTML) {
                    b = c.innerHTML
                } else {
                    var a = new XMLSerializer();
                    b = a.serializeToString(c)
                }
            } else {
                b = b.replace(/\r\n\t[\t]*/g, "").replace(/>\r\n/g, ">").replace(/\r\n/g, "\n")
            }
        }
        b = b.replace(/\n/g, "");
        // b = this._formatValue(b);
        return b
    },
    clearHTML: function() {
        var a = com.xjwgraph.Global;
        // a.undoRedoEventFactory.clear();
        a.lineTool.removeAll();
        a.modeTool.removeAll();
        a.baseTool.removeAll();
    },
    /**
     * chenjw1985@gmail.com
     * 20130504
     * XML to html
     * **/
    toHTML: function() {
        // 123
        var q = this,g = com.xjwgraph.Global,lineTool = g.lineTool;
        q.clearHTML();
        
        if (!q.doc) {
            return;
        }
        var d = q.doc.childNodes,
        a = d.length;
        for (var h = a; h--;) {
            if (d[h].nodeName == "flow") {
                q.root = q.doc.childNodes[h];
                break;
            }
        }
        if (q.root) {
            var p = q.root.childNodes,
            o = p.length,
            bp = g.baseBase.prop;

            for (var h = o; h--;) {
                var c = p[h],
                n = c.nodeName,
                l;
                /**
                chenjw1985@gmail.com
                20130817
                继续填个坑
                **/
                if(",flow_name,flow_create_time,flow_modified_time,flow_work_calendar,flow_initiate_tasks,flow_default_query_form".indexOf(","+n+",")>-1){
                    if(lineTool.isSVG)
                        bp[n] = c.textContent;
                    else if(lineTool.isVML)
                        bp[n] = c.text;
                }else{
                    if(n == "flow_seq"){
                        l = new ModeXML();
                    }else if(n == "flow_line"){
                        l = new LineXML();
                    }else if(n == "flow_child"){
                        l = new ChildXML();
                    }
                    var list = c.childNodes,ll=list.length;
                    for(;ll--;){
                        var b = list[ll];
                        if(lineTool.isSVG){
                            if(b.nodeName=='width'){
                                l.setAttribute(b.nodeName,50); 
                            }else{
                                l.setAttribute(b.nodeName,(b.textContent!=undefined?b.textContent:b.text)); 
                            }
                        }else if(lineTool.isVML){
                            if(b.nodeName=='width'){
                                l.setAttribute(b.nodeName,50);
                            }else{
                                l.setAttribute(b.nodeName,(b.text!=undefined?b.text:b.textContent));
                            }
                        }
                    }
                    l.view();
                }
            }
            var m = q.delay,
            k = m.length;
            for (var h = k; h--;) {
                m[h]()
            }
            delete q.delay;
            q.delay = [];
            q.delayIndex = 0
        }
    },
    appendProp:function(name,value,parent){
        var node = this.doc.createElement(name);
        var nodeValue = this.doc.createTextNode(value);
        node.appendChild(nodeValue);
        parent.appendChild(node);
    }
};
var client = com.xjwgraph.ClientTool = function(a) {
    this.dialog = $("#" + a.prop);
    $id(a.prop).style.width = a.dialogWidth || 328 + "px"
};
client.prototype = {
    _deepCopyProp: function(a) {
        var c = {};
        for (var b in a) {
            c[b] = a[b]
        }
        return c
    },
    _isDiffJson: function(d, c) {
        var b = false;
        for (var a in d) {
            if (d[a] !== c[a]) {
                b = true;
                break
            }
        }
        return b
    },
    _close: function(b) {
        var a = $(".panel-tool-close");
        if (a.length > 0) {
            a = a[0];
            a.click(b)
        }
    },
    showDialog: function(c, e, d) {
        var b = this._deepCopyProp(d.prop),
        a = this,
        lineTool = com.xjwgraph.Global.lineTool;
        this.dialog.dialog({
            title: e,
            modal: true,
            _attri_prop: null,
            buttons: [{
                text: "保存",
                handler: function() {
                    var i = d.prop;
                    // 123
                    for (var f in i) {
                        if(f=="line_arrow"){
                            var h = 0;
                            var ddd = $id(d.id);
                            if($id("lineAttr_" + f + "1").checked) {
                                if(lineTool.isSVG)
                                    ddd.setAttribute("marker-start","url(#larrow)");
                                if(lineTool.isVML)
                                    ddd.childNodes[0].setAttribute("StartArrow","classic");
                                h += 1;
                            }else{
                                if(lineTool.isSVG)
                                    ddd.removeAttribute("marker-start");
                                if(lineTool.isVML)
                                    ddd.childNodes[0].setAttribute("StartArrow","none");
                            }
                            if($id("lineAttr_" + f + "2").checked) {
                                if(lineTool.isSVG)
                                    ddd.setAttribute("marker-end","url(#arrow)");
                                if(lineTool.isVML)
                                    ddd.childNodes[0].setAttribute("EndArrow","classic");
                                h += 2;
                            }else{
                                if(lineTool.isSVG)
                                    ddd.removeAttribute("marker-end");
                                if(lineTool.isVML)
                                    ddd.childNodes[0].setAttribute("EndArrow","none");
                            }
                            i[f] = h;
                        }else if(f=="node_initiate_tasks" || f=="node_deadline_isworkday" || f=="node_allow_consultation" || f=="node_allow_delegation" || f=="node_allow_cc"){
                            //20131207
                            if($id("lineAttr_" + f)&&$id("lineAttr_" + f).checked){
                                i[f] = "true";
                            }else{
                                i[f] = "false";
                            }
                        }else{
                            if(f != "flow_id" && f != "node_id" && f != "line_id"){
                                var h = $id("lineAttr_" + f);
                                if(h){
                                    i[f] = h.value;
                                }
                            }
                        }
                        
                    }
                    a._close(c);
                    // 123
                    if(!!i["line_remark"])
                        d.div.html(i["line_remark"]);
                    if(!!i["node_name"])
                        $id("title" + d.id.match(/\d+/g)).innerHTML = i["node_name"];
                    if(!!i["node_sort_number"]){
                        $id("title" + d.id.match(/\d+/g)).innerHTML = $id("title" + d.id.match(/\d+/g)).innerHTML+"("+i["node_sort_number"]+")";
                    }
                    if(i["node_modified_time"] != undefined)
                        i["node_modified_time"] = GetTime();
                    // 起始任务修改
                    var bp = com.xjwgraph.Global.baseBase.prop;
                    if(d.type == "start" && bp.flow_initiate_tasks == "")
                        bp.flow_initiate_tasks = i["node_name"];
                    var g = new com.xjwgraph.UndoRedoEvent(function() {
                        d.prop = b
                    },
                    PathGlobal.editProp);
                    g.setRedo(function() {
                        d.prop = i
                    });
                }
            },
            {
                text: "重置",
                handler: function() {
                    var h = d.prop = b;
                    for (var f in h) {
                        if(f != "flow_id" && f != "node_id" && f != "line_id"){
                            if(",node_initiate_tasks,node_deadline_isworkday,node_allow_consultation,node_allow_delegation,node_allow_cc,".indexOf("," + f + ",") > -1){
                                var g = $id("lineAttr_" + f);
                                if(h[f] == "true"){
                                    g.checked = true;
                                }else if(h[f] == "false"){
                                    g.checked = false;
                                }
                            }
                            else if(f == "line_arrow"){
                                var g1 = $id("lineAttr_line_arrow1");
                                var g2 = $id("lineAttr_line_arrow2");
                                if(h[f]%2){
                                    g1.checked = true;
                                }else{
                                    g1.checked = false;
                                }
                                if(h[f]/2 >= 1){
                                    g2.checked = true;
                                }else{
                                    g2.checked = false;
                                }
                            }else{
                                var g = $id("lineAttr_" + f);
                                g.value = h[f];
                            }
                        }
                    }
                }
            }]
        })
    }
};
var Utils = com.xjwgraph.Utils = {
    create: function(e) {
        this.global = com.xjwgraph.Global;
        this.global.modeMap = new com.xjwgraph.Map();
        this.global.lineMap = new com.xjwgraph.Map();
        this.global.beanXml = new com.xjwgraph.BeanXML();
        var g = e.contextBody,
        a = $id(g),
        h = e.width,
        c = e.height;
        this.global.baseTool = new com.xjwgraph.BaseTool(a, h, c);
        this.global.modeTool = new com.xjwgraph.ModeTool(a);
        this.global.lineTool = new com.xjwgraph.LineTool(a);
        this.global.clientTool = new com.xjwgraph.ClientTool(e);
        this.global.undoRedoEventFactory = new com.xjwgraph.UndoRedoEventFactory();
        this.global.undoRedoEventFactory.init();
        // 123
        this.global.baseBase = new com.xjwgraph.BaseBase();
        return this
    },
    showLinePro: function() {
        //线条
        this.global.lineTool.showProperty()
    },
    showModePro: function() {
        //节点
        this.global.modeTool.showProperty()
    },
    showModeLct:function(){
    	//子流程
        this.global.modeTool.showModeLct()
    },
    showProcessPro: function() {
        //流程
        this.global.baseTool.showProperty()
    },
    /**
    purecolor@foxmail.com
    2013-12-07
    子流程节点属性
    **/
    showChildProcessPro: function() {
        //节点
        this.global.modeTool.showChildProperty()
    },
    // 555
    toTop: function() {
        this.global.modeTool.toTop()
    },
    // 555
    toBottom: function() {
        this.global.modeTool.toBottom()
    },
    undo: function() {
        this.global.undoRedoEventFactory.undo()
    },
    redo: function() {
        this.global.undoRedoEventFactory.redo()
    },
    saveXml: function() {
        this.global.beanXml.toXML()
    },
    loadXml: function() {
        this.global.beanXml.toHTML()
    },
    loadTextXml: function(a) {
        if(!a){
            return;
        }
        this.global.beanXml.doc = null;
        this.global.beanXml.doc = this.global.beanXml.loadXmlText(a);
        this.loadXml()
    },
    // 555
    clearHtml: function() {
        this.global.beanXml.clearHTML()
    },
    removeNode: function() {
        keyDownFactory.removeNode()
    },
    alignLeft: function() {
        this.global.baseTool.toLeft();
        this.global.baseTool.clearContext()
    },
    alignRight: function() {
        this.global.baseTool.toRight();
        this.global.baseTool.clearContext()
    },
    verticalCenter: function() {
        this.global.baseTool.toMiddleWidth();
        this.global.baseTool.clearContext()
    },
    alignTop: function() {
        this.global.baseTool.toTop();
        this.global.baseTool.clearContext()
    },
    horizontalCenter: function() {
        this.global.baseTool.toMiddleHeight();
        this.global.baseTool.clearContext()
    },
    bottomAlignment: function() {
        this.global.baseTool.toBottom();
        this.global.baseTool.clearContext()
    },
    nodeDrag: function(b, a, c) {
        this.global.baseTool.drag(b, a, c)
    }
};
// 123 修改
// 234 有疑问
// 345 有疑问
// 222 可以删除
// 333
// 滚动条控制问题，对齐线问题
// 555 预留功能
