const express = require('express');
const extensionBlockService = require('../services/extension-block-service');
const router = express.Router();

// 고정 확장자 목록 조회
router.get('/fixed-extensions/:userId', async (req, res) => {
    try {
        const userId = req.params.userId;
        const extensionData = await extensionBlockService.getFixedExtensions(userId);
        res.status(200).json(extensionData);
    } catch (error) {
        console.log(error.message);
        res.status(500).send(error.message);
    }
});

// 커스텀 확장자 목록 조회
router.get('/custom-extensions/:userId', async (req, res) => {
    const userId = req.params.userId;
    try {
        const customExtensions = await extensionBlockService.getCustomExtensions(userId);
        res.status(200).json(customExtensions);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

// 커스텀 확장자 추가
router.post('/custom-extensions/:userId', async (req, res) => {
    const userId = parseInt(req.params.userId);
    const extensionToAdd = req.body.extension; // POST 데이터는 body에서 추출
    try {
        const customExtension = await extensionBlockService.addCustomExtension(userId, extensionToAdd);
        const customExtensionResponse = {extension: customExtension};
        res.status(201).json(customExtensionResponse);
    } catch (error) {
        res.status(500).send(error.message);
    }
});

module.exports = router;
