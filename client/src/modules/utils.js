export const debounce = (handler, delay) => {
    let timerID = null;

    return function (...args) {
        clearTimeout(timerID);

        timerID = setTimeout(() => handler.apply(this, args), delay);
    }
};