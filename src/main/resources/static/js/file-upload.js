document.getElementById('fileUploadForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // 기본 동작 방지 (폼 제출 후 새고로침)

    const fileInput = document.getElementById('formFileMultiple');
    if (isEmptyFile(fileInput)) {
        return;
    }

    try {
        const response = await uploadFiles(this);
        handleUploadResponse(response);
    }catch (error){
        console.error('Error during upload:', error);
        alert('파일 업로드 중 오류 발생: ' + error.message); // 오류 메시지 표시
    }
});

const isEmptyFile = (input) => {
    if (input.files.length === 0) {
        alert('파일을 선택해 주세요.');
        return true;
    }
    return false;
}

const uploadFiles = async (form) => {
    const formData = new FormData(form);
    const response = await fetch(form.action, {
        method: 'POST',
        body: formData
    });

    if (!response.ok) {
        throw new Error('서버에서 오류가 발생했습니다. 상태 코드: ' + response.status);
    }
    return response.text();
}

const handleUploadResponse = (text) => {
    if (text) {
        const data = JSON.parse(text); // 응답 텍스트를 JSON으로 파싱
        validateData(data);
        return;
    }
    alert('파일 업로드 성공!'); // 응답 본문이 비어있는 경우
}

const validateData = (data) => {
    if (data.message) { // JSON에 message 필드가 있으면 이를 표시
        alert(data.message);
    } else {
        alert('파일 업로드 성공!'); // JSON 응답이지만 message 필드가 없는 경우
    }
}
