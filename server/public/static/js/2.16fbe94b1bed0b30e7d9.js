webpackJsonp([2],{131:function(e,t,n){function a(e){n(610)}var o=n(51)(n(589),n(626),a,null,null);e.exports=o.exports},219:function(e,t,n){e.exports={default:n(221),__esModule:!0}},220:function(e,t,n){"use strict";t.__esModule=!0;var a=n(219),o=function(e){return e&&e.__esModule?e:{default:e}}(a);t.default=o.default||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var a in n)Object.prototype.hasOwnProperty.call(n,a)&&(e[a]=n[a])}return e}},221:function(e,t,n){n(225),e.exports=n(3).Object.assign},222:function(e,t,n){"use strict";var a=n(54),o=n(223),i=n(224),A=n(55),s=n(53),r=Object.assign;e.exports=!r||n(28)(function(){var e={},t={},n=Symbol(),a="abcdefghijklmnopqrst";return e[n]=7,a.split("").forEach(function(e){t[e]=e}),7!=r({},e)[n]||Object.keys(r({},t)).join("")!=a})?function(e,t){for(var n=A(e),r=arguments.length,l=1,u=o.f,c=i.f;r>l;)for(var p,f=s(arguments[l++]),d=u?a(f).concat(u(f)):a(f),B=d.length,C=0;B>C;)c.call(f,p=d[C++])&&(n[p]=f[p]);return n}:r},223:function(e,t){t.f=Object.getOwnPropertySymbols},224:function(e,t){t.f={}.propertyIsEnumerable},225:function(e,t,n){var a=n(7);a(a.S+a.F,"Object",{assign:n(222)})},589:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=n(220),o=n.n(a),i=n(56),A=n(27),s=n(11);t.default={computed:o()({},n.i(i.b)(["title","warns","menus","menuDesc"])),watch:{menus:function(e){var t=this;e.length&&(e.forEach(function(e){t.openMenuNames.push(e.menuName)}),this.$nextTick(function(){var e=t.$refs.menu;e.updateActiveName(),e.updateOpened()}))},menuDesc:function(e){e&&this._setCurrentTitle()}},created:function(){this.openMenuNames=[],this.getMenu(),this._setCurrentRoute()},mounted:function(){var e=this;this.setData(),this.interval=window.setInterval(function(){e.setData()},s.n)},deactivated:function(){this.interval&&window.clearInterval(this.interval)},methods:o()({menuSelected:function(e){this.$router.push({path:e})}},n.i(i.c)({setTitle:A.a}),n.i(i.d)(["setData","getMenu"]),{_setCurrentRoute:function(){var e=this.$router.currentRoute.path;"/"===e?(this.$router.push({path:"/bases"}),this.activeMenu="/bases"):this.activeMenu=e},_setCurrentTitle:function(){var e=this.$router.currentRoute.path;"/"===e&&(e="/bases"),this.setTitle(this.menuDesc[e])}})}},597:function(e,t,n){t=e.exports=n(128)(!0),t.push([e.i,".logo{height:30px;width:30px;border-radius:50%;position:absolute;left:12px}.layout{border:1px solid #d7dde4;background:#f5f7f9;position:relative}.layout-header{display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-pack:center;-ms-flex-pack:center;justify-content:center;-webkit-box-align:center;-ms-flex-align:center;align-items:center}.layout-content{min-height:200px;margin:15px;overflow:hidden;background:#fff;border-radius:4px}.layout-content-main{padding:10px}.layout-copy{text-align:center;padding:10px 0 20px;color:#9ea7b4}.layout-menu-left{background:#464c5b}.layout-header{height:60px;background:#fff;-webkit-box-shadow:0 1px 1px rgba(0,0,0,.1);box-shadow:0 1px 1px rgba(0,0,0,.1)}.layout-logo-left{width:90%;height:30px;font-size:18px;font-weight:600;color:#fff;background:#495060;border-radius:3px;margin:15px auto;display:-webkit-box;display:-ms-flexbox;display:flex;-webkit-box-pack:center;-ms-flex-pack:center;justify-content:center;-webkit-box-align:center;-ms-flex-align:center;align-items:center}.ivu-table .table-info-row td{background-color:#2db7f5;color:#fff;font-size:16px}.badge{border-radius:6px;display:inline-block;width:90px}","",{version:3,sources:["/Users/nichen/src/vue/github/state/frontend/src/components/layout.vue"],names:[],mappings:"AACA,MACE,YAAa,AACb,WAAY,AACZ,kBAAmB,AACnB,kBAAmB,AACnB,SAAW,CACZ,AACD,QACE,yBAA0B,AAC1B,mBAAoB,AACpB,iBAAmB,CACpB,AACD,eACE,oBAAqB,AACrB,oBAAqB,AACrB,aAAc,AACd,wBAAyB,AACrB,qBAAsB,AAClB,uBAAwB,AAChC,yBAA0B,AACtB,sBAAuB,AACnB,kBAAoB,CAC7B,AACD,gBACE,iBAAkB,AAClB,YAAa,AACb,gBAAiB,AACjB,gBAAiB,AACjB,iBAAmB,CACpB,AACD,qBACE,YAAc,CACf,AACD,aACE,kBAAmB,AACnB,oBAAqB,AACrB,aAAe,CAChB,AACD,kBACE,kBAAoB,CACrB,AACD,eACE,YAAa,AACb,gBAAiB,AACjB,4CAAiD,AACzC,mCAAyC,CAClD,AACD,kBACE,UAAW,AACX,YAAa,AACb,eAAgB,AAChB,gBAAiB,AACjB,WAAY,AACZ,mBAAoB,AACpB,kBAAmB,AACnB,iBAAkB,AAClB,oBAAqB,AACrB,oBAAqB,AACrB,aAAc,AACd,wBAAyB,AACrB,qBAAsB,AAClB,uBAAwB,AAChC,yBAA0B,AACtB,sBAAuB,AACnB,kBAAoB,CAC7B,AACD,8BACE,yBAA0B,AAC1B,WAAY,AACZ,cAAgB,CACjB,AACD,OACE,kBAAmB,AACnB,qBAAsB,AACtB,UAAY,CACb",file:"layout.vue",sourcesContent:["\n.logo {\n  height: 30px;\n  width: 30px;\n  border-radius: 50%;\n  position: absolute;\n  left: 12px;\n}\n.layout {\n  border: 1px solid #d7dde4;\n  background: #f5f7f9;\n  position: relative;\n}\n.layout-header {\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-pack: center;\n      -ms-flex-pack: center;\n          justify-content: center;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center;\n}\n.layout-content {\n  min-height: 200px;\n  margin: 15px;\n  overflow: hidden;\n  background: #fff;\n  border-radius: 4px;\n}\n.layout-content-main {\n  padding: 10px;\n}\n.layout-copy {\n  text-align: center;\n  padding: 10px 0 20px;\n  color: #9ea7b4;\n}\n.layout-menu-left {\n  background: #464c5b;\n}\n.layout-header {\n  height: 60px;\n  background: #fff;\n  -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n          box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n}\n.layout-logo-left {\n  width: 90%;\n  height: 30px;\n  font-size: 18px;\n  font-weight: 600;\n  color: #fff;\n  background: #495060;\n  border-radius: 3px;\n  margin: 15px auto;\n  display: -webkit-box;\n  display: -ms-flexbox;\n  display: flex;\n  -webkit-box-pack: center;\n      -ms-flex-pack: center;\n          justify-content: center;\n  -webkit-box-align: center;\n      -ms-flex-align: center;\n          align-items: center;\n}\n.ivu-table .table-info-row td {\n  background-color: #2db7f5;\n  color: #fff;\n  font-size: 16px;\n}\n.badge {\n  border-radius: 6px;\n  display: inline-block;\n  width: 90px;\n}\n"],sourceRoot:""}])},610:function(e,t,n){var a=n(597);"string"==typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);n(129)("7584643a",a,!0)},626:function(e,t){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"layout"},[n("Row",{attrs:{type:"flex"}},[n("i-col",{staticClass:"layout-menu-left",attrs:{span:"4"}},[n("Menu",{ref:"menu",attrs:{theme:"dark","active-name":e.activeMenu,width:"auto","open-names":e.openMenuNames},on:{"on-select":e.menuSelected}},[n("div",{staticClass:"layout-logo-left"},[n("img",{staticClass:"logo",attrs:{src:"static/img/logo.gif"}}),e._v(" "),n("span",[e._v("超级监控系统")])]),e._v(" "),e._l(e.menus,function(t){return n("Submenu",{key:t.menuName,attrs:{name:t.menuName}},[n("template",{slot:"title"},[n("Icon",{attrs:{type:"ios-navigate"}}),e._v("\n                        "+e._s(t.menuName)+"\n                    ")],1),e._v(" "),e._l(t.subMenus,function(t){return n("Menu-item",{key:t.name,attrs:{name:t.path}},[n("Badge",{attrs:{count:e.warns[t.name]}},[n("span",{staticClass:"badge"},[e._v(e._s(t.display))])])],1)})],2)})],2)],1),e._v(" "),n("i-col",{attrs:{span:"20"}},[n("div",{staticClass:"layout-header"},[n("h1",[e._v(e._s(e.title))])]),e._v(" "),n("div",{staticClass:"layout-content"},[n("div",{staticClass:"layout-content-main"},[n("keep-alive",[n("router-view")],1)],1)]),e._v(" "),n("div",{staticClass:"layout-copy"},[e._v("\n                xxx\n            ")])])],1)],1)},staticRenderFns:[]}}});
//# sourceMappingURL=2.16fbe94b1bed0b30e7d9.js.map