const FixedFileExtension = require('../../../models/fixed-file-extension');
const CustomFileExtension = require('../../../models/custom-file-extension');

const getFixedExtensions = async (userId) => {
    const fixedExtensions = await FixedFileExtension.findAll({
        where: {
            userId: userId
        }
    });
    return fixedExtensions.map(extension => ({
        "extension": extension.fileExtension,
        "isChecked": extension.restricted
    }));
};

const getCustomExtensions = async (userId) => {
    const customExtensions = await CustomFileExtension.findAll({
        where: {
            userId: userId
        }
    });
    return customExtensions.map(extension => ({
        "extension": extension.fileExtension
    }));
};

module.exports = {getFixedExtensions, getCustomExtensions}
