package com.annualLeave.framework.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

//    private ErrorDefService errorDefService;
//    private ErrorLogService errorLogService;
//    private ErrorLogMapper errorLogMapper;
//    private SessionContext sesion;
//
//
//    @Autowired
//    public GlobalRestExceptionHandler(ErrorDefService errorDefService, ErrorLogService errorLogService,
//                                      ErrorLogMapper errorLogMapper, SessionContext sesion) {
//        super();
//        this.errorDefService = errorDefService;
//        this.errorLogService = errorLogService;
//        this.errorLogMapper = errorLogMapper;
//        this.sesion = sesion;
//    }
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<ErrorLogDto> handleGenericException(Exception e, WebRequest request) {
//        e.printStackTrace();
//        ResponseEntity<ErrorLogDto> responseEntity = customExceptionControl(e);
//        if (responseEntity != null) {
//            return responseEntity;
//        } else {
//            try {
//                ErrorLog errorLog = errorLogService.createErrorLog(null, "SYS.0001", null, e.getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.BAD_REQUEST);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                return null;
//            }
//        }
//    }
//
//    private ResponseEntity<ErrorLogDto> customExceptionControl(Exception e) {
//        try {
//            ErrorLog errorLog = null;
//            if (e instanceof ObjectOptimisticLockingFailureException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0002", null, "versionChanged");
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.BAD_REQUEST);
//            } else if (e instanceof AuthenticateException) {
//                errorLog = errorLogService.createErrorLog(null, "TKN.0001", null, e.getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.UNAUTHORIZED);
//            } else if (e instanceof NoPermissionException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0005", null, e.getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.FORBIDDEN);
//            } else if (e instanceof DataIntegrityViolationException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0008", null, ExceptionUtils.getRootCause(e).getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.INTERNAL_SERVER_ERROR);
//            } else if (e instanceof PSQLException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0009", null, ExceptionUtils.getRootCause(e).getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.INTERNAL_SERVER_ERROR);
//            } else if (e instanceof InvalidDataAccessResourceUsageException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0010", null, ExceptionUtils.getRootCause(e).getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.INTERNAL_SERVER_ERROR);
//            } else if (e instanceof SQLGrammarException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0012", null, ExceptionUtils.getRootCause(e).getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.INTERNAL_SERVER_ERROR);
//            } else if (e instanceof PersistenceException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0013", null, ExceptionUtils.getRootCause(e).getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.INTERNAL_SERVER_ERROR);
//            } else if (e instanceof NullPointerException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0014", null, ExceptionUtils.getStackTrace(e));
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.BAD_REQUEST);
//            } else if (e instanceof SQLTransientConnectionException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0017", null, ("systemBusy"));
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.TOO_MANY_REQUESTS);
//            } else if (e instanceof ConstraintViolationException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0018", null, ExceptionUtils.getRootCause(e).getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.INTERNAL_SERVER_ERROR);
//            } else if (e instanceof NakilixException) {
//                NakilixException nakilixException = (NakilixException) e;
//                ErrorDef errorDef = errorDefService.findByCodeAndLanguage(nakilixException.getErrorCode(), sesion.getUser());
//                String error = null;
//                if (errorDef != null) {
//                    error = (errorDef.getError());
//                    if (!nakilixException.getCustomErrorList().isEmpty()) {
//                        for (int i = 1; i <= nakilixException.getCustomErrorList().size(); i++) {
//                            error = error.replaceAll("\\?" + String.valueOf(i), nakilixException.getCustomErrorList().get(i - 1));
//                        }
//                    }
//                }
//                if (error == null || error.trim().isEmpty() || "null".equals(error)) {
//                    error = nakilixException.getErrorText();
//                    System.out.println("nakilixException.() => "+nakilixException);
//                    System.out.println("nakilixException.getCustomErrorList() => "+nakilixException.getCustomErrorList());
//                    if (nakilixException.getCustomErrorList()!=null && !nakilixException.getCustomErrorList().isEmpty()) {
//                        for (int i = 1; i <= nakilixException.getCustomErrorList().size(); i++) {
//                            error = error.replaceAll("\\?" + String.valueOf(i), nakilixException.getCustomErrorList().get(i - 1));
//                        }
//                    }
//                }
//                errorLog = errorLogService.createErrorLog(nakilixException.getEntity(), nakilixException.getErrorCode(), errorDef, error);
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.BAD_REQUEST);
//            } else if (e instanceof RuntimeException) {
//                errorLog = errorLogService.createErrorLog(null, "SYS.0019", null, e.getMessage());
//                return new ResponseEntity<ErrorLogDto>(errorLogMapper.map(errorLog), HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
}
