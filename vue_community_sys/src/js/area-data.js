import REGION_DATA from 'china-area-data'

const provinceDataList = []
const textCodeMap = new Map();
const cityDataMap = new Map();
const _province = REGION_DATA['86']

for (const prop in _province) {
    provinceDataList.push({
        value: prop,
        label: _province[prop]
    })
    textCodeMap.set(_province[prop], prop);
}

for (let i = 0, len = provinceDataList.length; i < len; i++) {
    const provinceCode = provinceDataList[i].value
    let cityList = []
    let _cityData = REGION_DATA[provinceCode]
    for (const prop in _cityData) {
        cityList.push({
            value: prop,
            label: _cityData[prop]
        })
        textCodeMap.set(_cityData[prop], prop);
    }
    cityDataMap.set(provinceCode, cityList);
}

export { provinceDataList, cityDataMap, textCodeMap }