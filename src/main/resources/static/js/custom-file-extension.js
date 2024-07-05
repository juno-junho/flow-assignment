
const MAX_CUSTOM_EXTENSION_COUNT = 200;
const USER_ID = 1; // userId 1로 고정

document.addEventListener('DOMContentLoaded', async function () {
    await fetchCustomExtensions();
});

const fetchCustomExtensions = async () => {
    try {
        const response = await fetch(`https://extension-block.store/api/v1/extension-blocks/custom-extensions/${USER_ID}`);
        const data = await response.json();
        data.forEach(item => addExtensionBlock(item.extension));
    } catch (error) {
        console.error('Error fetching custom extensions:', error);
    }
}

const deleteCustomExtension = async (extension, badge) => {
    const formData = new URLSearchParams();
    formData.append('extension', extension);
    try {
        const response = await fetch(`https://extension-block.store/api/v1/extension-blocks/custom-extensions/${USER_ID}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        });

        if (response.ok) {
            badge.remove(); // 화면 배지 제거
            updateCustomExtensionCount(); // 확장자가 제거될 때마다 개수 업데이트
        } else {
            throw new Error('Failed to delete the extension');
        }
    } catch (error) {
        console.error('Error deleting custom extension', error);
    }
}

const addCustomExtension = async () => {
    const input = document.getElementById('customExt');
    const extension = input.value.trim();  // 입력값을 가져오고 공백 제거

    if(!extension){
        alert('확장자를 입력해주세요.');
        return;
    }
    const formData = new URLSearchParams();
    formData.append('extension', extension);

    try{
        const response = await fetch(`https://extension-block.store/api/v1/extension-blocks/custom-extensions/${USER_ID}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        });
        const data = await response.json();
        if (data.code) {
            alert(data.message);
        } else {
            addExtensionBlock(extension);
        }
    }catch (error){
        console.error('Error adding custom extension:', error);
        alert('Error: ' + error.message);
    }
    input.value = '';// 요청 후 입력 필드 초기화
}

const addExtensionBlock = (extension) => {
    const extensionList = document.getElementById('extList');

    if (extension !== "" && extensionList.children.length <= MAX_CUSTOM_EXTENSION_COUNT) {
        const badge = document.createElement('div');
        badge.classList.add('badge', 'bg-secondary', 'm-1');
        badge.textContent = extension.toLowerCase() + " ";

        const removeButton = document.createElement('button');
        removeButton.classList.add('btn-close');
        removeButton.onclick = () =>  {
            deleteCustomExtension(extension, badge).then(console.log); // 항목이 제거될 때 개수 업데이트
        };
        badge.appendChild(removeButton);
        extensionList.appendChild(badge);
        updateCustomExtensionCount(); // 항목이 추가될 때 개수 업데이트
    }
}

const updateCustomExtensionCount = () =>  {
    const count = document.getElementsByClassName('badge').length;
    document.getElementById('extCount').textContent = `${count}/${MAX_CUSTOM_EXTENSION_COUNT}`;
}
