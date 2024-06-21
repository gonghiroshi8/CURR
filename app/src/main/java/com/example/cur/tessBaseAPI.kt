tessBaseAPI = TessBaseAPI()
val dataPath = filesDir.toString() + "/tesseract/"
val lang = "eng"
tessBaseAPI.init(dataPath, lang)
