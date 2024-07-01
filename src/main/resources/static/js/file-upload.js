document.getElementById('fileUploadForm').addEventListener('submit', function (e) {
    e.preventDefault(); // 폼 기본 제출 동작 방지

    const formData = new FormData(this);
    fetch(this.action, {
        method: 'POST',
        body: formData, // FormData를 POST 요청 본문으로 설정
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버에서 오류가 발생했습니다. 상태 코드: ' + response.status);
            }
            return response.text(); // 응답을 텍스트로 변환
        })
        .then(text => {
            if (text) {
                const data = JSON.parse(text); // 응답 텍스트를 JSON으로 파싱
                if (data.message) {
                    alert(data.message); // JSON에 message 필드가 있으면 이를 표시
                } else {
                    alert('파일 업로드 성공!'); // JSON 응답이지만 message 필드가 없는 경우
                }
            } else {
                alert('파일 업로드 성공!'); // 응답 본문이 비어있는 경우
            }
        })
        .catch(error => {
            console.error('Error during upload:', error);
            alert('파일 업로드 중 오류 발생: ' + error.message); // 오류 메시지 표시
        });
});
