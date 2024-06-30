document.addEventListener('DOMContentLoaded', function () {
    fetchFixedExtensions();
});

function fetchFixedExtensions() {
    const userId = 1; // 유저ID는 1로 고정
    fetch('http://localhost:8080/api/v1/extension-blocks/fixed-extensions/' + userId)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const container = document.getElementById('fixedExtensionList');
            data.forEach(item => {
                const div = document.createElement('div');
                div.className = 'form-check form-check-inline';

                const input = document.createElement('input');
                input.type = 'checkbox';
                input.className = 'form-check-input';
                input.id = item.extension;
                input.value = item.extension;
                input.checked = item.isChecked;
                input.onchange = function () {
                    updateExtension(this);
                };

                const label = document.createElement('label');
                label.className = 'form-check-label';
                label.htmlFor = item.extension;
                label.textContent = item.extension;

                div.appendChild(input);
                div.appendChild(label);
                container.appendChild(div);
            });
        })
        .catch(error => console.error('Error loading the extensions:', error));
}

function updateExtension(checkbox) {
    const extension = checkbox.value;
    const isChecked = checkbox.checked;

    const userId = 1; // 유저ID는 1로 고정
    // 서버로 PATCH 요청 보내기
    fetch('http://localhost:8080/api/v1/extension-blocks/custom-extensions/' + userId, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            extension: extension,
            isChecked: isChecked
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
        })
        .catch(error => console.error('Error:', error));
}

