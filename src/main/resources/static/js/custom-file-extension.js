
document.addEventListener('DOMContentLoaded', function() {
    fetchCustomExtensions();
});

function fetchCustomExtensions() {
    const userId = 1;  // 유저ID는 1로 고정
    fetch('http://localhost:8080/api/v1/extension-blocks/custom-extensions/'+userId)
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('extList');
            data.forEach(item => {
                const badge = document.createElement('div');
                badge.className = 'badge bg-secondary m-1';
                badge.textContent = item.extension + " "; // 확장자명을 텍스트로 추가

                const closeButton = document.createElement('button');
                closeButton.className = 'btn-close';
                closeButton.onclick = function() {
                    deleteCustomExtension(item.extension, badge); // 클릭 시 확장자를 제거
                };

                badge.appendChild(closeButton);
                container.appendChild(badge);
                updateCustomExtensionCount(); // 확장자가 추가될 때마다 개수 업데이트
            });
        })
        .catch(error => console.error('Error:', error));
}

function deleteCustomExtension(extension, badge) {
    const formData = new URLSearchParams();
    formData.append('extension', extension);

    fetch('http://localhost:8080/api/v1/extension-blocks/custom-extensions/1', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
        .then(response => {
            if (response.ok) {  // 성공적으로 삭제된 경우
                badge.remove(); // 화면에서 배지 요소 제거
                updateCustomExtensionCount(); // 확장자가 제거될 때마다 개수 업데이트
            } else {
                throw new Error('Failed to delete the extension');
            }
        })
        .catch(error => console.error('Error deleting extension:', error));
}

function addCustomExtension() {
    const input = document.getElementById('customExt');
    const extension = input.value.trim();  // 입력값을 가져오고 공백 제거
    console.log('Extension:', extension)
    if (extension) {  // 입력값이 있는 경우에만 요청 수행
        const url = 'http://localhost:8080/api/v1/extension-blocks/custom-extensions/1';
        const formData = new URLSearchParams();
        formData.append('extension', extension);

        console.log('Requesting to:', url);
        console.log('Request body:', formData.toString());
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        }).then(response => response.json())
            .then(data => {
                if (data.code) {
                    alert(data.message); // 서버에서 반환된 에러 메시지를 사용자에게 알림
                } else {
                    console.log('Success:', data);
                    addExtensionBlock(extension); // 에러가 없을 경우에만 addExtensionBlock 실행
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error: ' + error.message);
            });
        input.value = '';  // 요청 후 입력 필드 초기화
    } else {
        alert('확장자를 입력해주세요.');
    }
}

function addExtensionBlock(extension) {
    console.log("addExtensionBlock started");
    let list = document.getElementById('extList');

    if (extension !== "" && list.children.length < 201) {
        var entry = document.createElement('div');
        entry.classList.add('badge', 'bg-secondary', 'm-1');
        entry.textContent = extension.toLowerCase() + " ";
        var removeBtn = document.createElement('button');
        removeBtn.classList.add('btn-close');
        removeBtn.onclick = function () {
            entry.remove();
            updateCustomExtensionCount(); // 항목이 제거될 때 개수 업데이트
        };
        entry.appendChild(removeBtn);
        list.appendChild(entry);
        updateCustomExtensionCount(); // 항목이 추가될 때 개수 업데이트
    }
}

function updateCustomExtensionCount() {
    const count = document.getElementsByClassName('badge').length;
    const maxCount = 200; // 최대 배지 개수 설정
    console.log('Count:', count, 'Max count:', maxCount);
    document.getElementById('extCount').textContent = `${count}/${maxCount}`;
}
