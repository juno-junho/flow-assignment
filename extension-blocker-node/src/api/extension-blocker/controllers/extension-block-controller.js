const express = require('express');
const router = express.Router();
const extensionBlockService = require('../services/extension-block-service');

router.get('/fixed-extensions/:userId', async (req, res) => {
    try {
        // const fixedExtensions = await extensionBlockService.getFixedExtensions(userId);
        const fixedExtensions = {a: "test"};

        res.status(200).json(fixedExtensions);
    } catch (error) {
        console.log(error.message);
        res.status(500).send(error.message);
    }
});

router.get('/custom-extensions/:userId', async (req, res) => {
    const userId = req.params.userId;
    try {
        console.log('custom-extensions/:userId called');
        const customExtensions = await extensionBlockService.getCustomExtensions(userId);
        res.status(200).json(customExtensions);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// 라우트: 사용자 정의 확장자 추가
router.post('/custom-extensions/:userId', async (req, res) => {
    const userId = parseInt(req.params.userId);
    const extension = req.body.extension; // POST 데이터는 body에서 추출
    try {
        console.log('custom-extensions/:userId called');
        const result = await extensionBlockService.addCustomExtension(userId, extension);
        res.status(201).json(result);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// 라우트: 파일 업로드
router.post('/upload/:userId', async (req, res) => {
    const userId = parseInt(req.params.userId);
    const file = req.files.file; // 'express-fileupload' 사용 또는 비슷한 미들웨어 필요
    try {
        console.log('upload/:userId called');
        await extensionBlockService.uploadFile(userId, file);
        res.sendStatus(200);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// 라우트: 고정 확장자 상태 업데이트
router.patch('/custom-extensions/:userId', async (req, res) => {
    const userId = parseInt(req.params.userId);
    try {
        console.log('custom-extensions/:userId called');
        await extensionBlockService.checkFixedExtension(userId, req.body);
        res.sendStatus(204);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// 라우트: 사용자 정의 확장자 삭제
router.delete('/custom-extensions/:userId', async (req, res) => {
    const userId = parseInt(req.params.userId);
    const extensions = req.query.extensions; // 쿼리 파라미터로 확장자 리스트를 받음
    try {
        console.log('custom-extensions/:userId called');
        await extensionBlockService.deleteCustomExtension(userId, extensions);
        res.sendStatus(204);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

module.exports = router;
