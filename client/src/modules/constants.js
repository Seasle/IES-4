export const SERVER_URL = 'http://25.57.45.239:8080';

export const API_URL = `${SERVER_URL}/api`;

export const FILES_URL = `${SERVER_URL}/files`;

export const MODULE_NAMES = new Map([
    ['powerSupply', 'Энергоснабжение'],
    ['waterSupply', 'Водоснабжение'],
    ['climateSupply', 'Система климата'],
    ['equipmentSupply', 'Аппаратура'],
    ['householdApplianceSupply', 'Бытовые приборы'],
    ['videoMonitoringSupply', 'Видеонаблюдение'],
    ['securitySupply', 'Модуль охраны'],
    ['controlSupply', 'Модуль управления']
]);