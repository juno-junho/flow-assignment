
const currentUrl = window.location.href;
const url = new URL(currentUrl);

export const urlPrefix = `${url.protocol}//${url.host}`;
console.log(urlPrefix);

