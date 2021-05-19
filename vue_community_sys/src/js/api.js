var boolToCN = {
  "true": "是",
  "false": "否"
}
export function changeBoolToCN(list, props) {
  for(var i in list) {
    var item = list[i]
    for(var p in props) {
      var value = props[p]
      item[value] = boolToCN[item[value].toString()]
    }
  }
}
