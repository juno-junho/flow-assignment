import {userId} from "./user.mjs";
import {urlPrefix} from "./url-info.mjs";

document.addEventListener('DOMContentLoaded', async function () {
    await fetchFixedExtensions();
});

const fetchFixedExtensions = async () => {
    try {
        const response = await fetch(`${urlPrefix}/api/v1/extension-blocks/fixed-extensions/${userId}`);
        const data = await response.json();
        showFixedExtensions(data);
    } catch (error) {
        console.error('Error fetching fixed extensions:', error);
    }
}

const showFixedExtensions = (data) => {
    const container = document.getElementById('fixedExtensionList');
    data.forEach(item => {
        const div = document.createElement('div');
        div.className = 'form-check form-check-inline';

        const input = createCheckbox(item);
        const label = createLabel(item);

        div.appendChild(input);
        div.appendChild(label);
        container.appendChild(div);
    });
}

const createCheckbox = (item) => {
    const input = document.createElement('input');
    input.type = 'checkbox';
    input.className = 'form-check-input';
    input.id = item.extension;
    input.value = item.extension;
    input.checked = item.isChecked;
    input.onchange = function () {
        updateExtension(this);
    };
    return input;
}

const createLabel = (item) => {
    const label = document.createElement('label');
    label.className = 'form-check-label';
    label.htmlFor = item.extension;
    label.textContent = item.extension;
    return label;
}

const updateExtension = async (checkbox) => { // 서버로 PATCH 요청 보내기
    const extension = checkbox.value;
    const isChecked = checkbox.checked;

    try {
        const response = await fetch(`${urlPrefix}/api/v1/extension-blocks/custom-extensions/${userId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                extension: extension,
                isChecked: isChecked
            })
        });
        if (!response.ok) {
            console.error('patch 요청 실패!');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
