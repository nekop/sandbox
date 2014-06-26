;;;; -*- mode: lisp-interaction; coding: iso-2022-7bit -*-

;; package
(when (>= emacs-major-version 24)
  (require 'package)
  (package-initialize)
  (add-to-list 'package-archives '("melpa" . "http://melpa.milkbox.net/packages/") t)
  ;; helm
  (global-set-key (kbd "C-;") 'helm-for-files)
  (eval-after-load 'helm
    '(progn
       (define-key helm-map (kbd "C-h") 'backward-delete-char)
       ))
  ;; yasnippet
  (yas-global-mode 1)
  ;; twittering-mode
  (setq twittering-username "nekop")
  (setq twittering-use-native-retweet t)
  (setq twittering-use-master-password t)
  ;;(setq twittering-debug-mode t)
  ;; malabar-mode
  (require 'cedet)
  (require 'malabar-mode)
  (add-to-list 'auto-mode-alist '("\\.java\\'" . malabar-mode))
  )

(global-font-lock-mode t)
;;(setq debug-on-error t)

;;; Font
;; (prin1-to-string (x-list-fonts "*")) ;; available fonts
(cond
 (window-system (set-default-font "DejaVu Sans Mono-8")
                (set-fontset-font (frame-parameter nil 'font)
                                  'japanese-jisx0208
                                  '("DejaVu Sans Mono-8" . "unicode-bmp"))))


;;; Frame
(setq default-frame-alist
      (append (list
               '(foreground-color . "black")
;;               '(background-color . "LemonChiffon")
               '(cursor-type . box)
;;               '(height . 80) ;; for 1960x1080
;;               '(width . 113) ;; for 1960x1080
               '(height . 73) ;; for 1280x1024
               '(width . 80) ;; for 1280x1024
;;               '(height . 63) ;; for 1280x800
;;               '(width . 80) ;; for 1280x800
               '(top . 0)
               '(left . 1080))
              default-frame-alist))

(show-paren-mode t)
(setq visible-bell t)
(menu-bar-mode -1)
(tool-bar-mode -1)
(scroll-bar-mode t)
(line-number-mode t)
(column-number-mode t)
(setq transient-mark-mode t)
(setq dabbew-case-fold-search nil)
(defadvice dabbrev-expand
  (around modify-regexp-for-japanese activate compile)
  "Modify `dabbrev-abbrev-char-regexp' dynamically for Japanese words."
  (if (bobp)
      ad-do-it
    (let ((dabbrev-abbrev-char-regexp
           (let ((c (char-category-set (char-before))))
             (cond 
              ((aref c ?a) "[-_A-Za-z0-9]") ; ASCII
              ((aref c ?j)              ; Japanese
               (cond
                ((aref c ?K) "\\cK")    ; katakana
                ((aref c ?A) "\\cA")    ; 2byte alphanumeric
                ((aref c ?H) "\\cH")    ; hiragana
                ((aref c ?C) "\\cC")    ; kanji
                (t "\\cj")))
              ((aref c ?k) "\\ck")      ; hankaku-kana
              ((aref c ?r) "\\cr")      ; Japanese roman ?
              (t dabbrev-abbrev-char-regexp)))))
      ad-do-it)))
(setq dabbrev-case-fold-search nil)
(show-paren-mode t)
(setq kill-whole-line t)
(setq-default scroll-step 1)
(defun previous-line (arg)
  (interactive "p")
  (if (interactive-p)
      (condition-case nil (line-move (- arg))
        ((beginning-of-buffer end-of-buffer)))
    (line-move (- arg)))
  nil)
(setq next-line-add-newlines nil)
(setq require-final-newline nil)
(setq-default tab-width 4 indent-tabs-mode nil)
(setq truncate-partial-width-windows nil)
(iswitchb-mode)
(global-set-key (kbd "C-x C-b") 'electric-buffer-list)
(global-set-key [mouse-2] 'ignore)
(let ((elem (assq 'encoded-kbd-mode minor-mode-alist)))
  (when elem
    (setcar (cdr elem) "")))
(global-set-key (kbd "C-h") 'backward-delete-char)
(global-set-key (kbd "M-g") 'goto-line)
(define-key ctl-x-map "p"
  '(lambda (arg) (interactive "p") (other-window (- arg))))
(defun isearch-yank-char ()
  "Pull next character from buffer into search string."
  (interactive)
  (isearch-yank-string
   (save-excursion
     (and (not isearch-forward) isearch-other-end
          (goto-char isearch-other-end))
     (buffer-substring (point) (1+ (point))))))
(define-key isearch-mode-map (kbd "C-d") 'isearch-yank-char)
(define-key isearch-mode-map (kbd "C-k") 'isearch-edit-string)
(defun confirm-kill-emacs ()
  (interactive)
  (if (yes-or-no-p "quit emacs? ")
      (save-buffers-kill-emacs)))
(global-set-key (kbd "C-x C-c") 'confirm-kill-emacs)
(defun firefox (file-name)
  (interactive "fFilename: ")
  (start-process "firefox" nil "firefox" (expand-file-name file-name)))
(defun firefox-from-buffer ()
  (interactive)
  (if buffer-file-name
      (start-process "firefox" nil "firefox" buffer-file-name)
    (error "This buffer is not visiting a file")))
(require 'uniquify)
(setq uniquify-buffer-name-style 'post-forward-angle-brackets)
(defun ediff-with-original ()
  (interactive)
  (let ((file buffer-file-name)
        (buf (current-buffer))
        (orig-buf (get-buffer-create (concat "*orig " buffer-file-name "*"))))
    (set-buffer orig-buf)
    (setq buffer-read-only nil)
    (buffer-disable-undo)
    (erase-buffer)
    (insert-file file)
    (setq buffer-read-only t)
    (set-buffer-modified-p nil)
    (ediff-buffers orig-buf buf)))
(defun trim ()
  "Delete excess whitespaces."
  (interactive "*")
  (let ((cms (y-or-n-p "Convert multiple spaces? ")))
    (save-excursion
      (save-restriction
        (and transient-mark-mode
             mark-active
             (narrow-to-region (region-beginning) (region-end)))
        (goto-char (point-min))
        (while (re-search-forward "[ \t]+$" nil t)
          (replace-match "" nil nil))
        (goto-char (point-max))
        (delete-blank-lines)
        (when cms
          (tabify (point-min) (point-max))))))
  (deactivate-mark))


;;; archive-mode
;;(setq archive-zip-use-pkzip nil)
(setq auto-mode-alist (cons '("\\.war$" . archive-mode) auto-mode-alist))
(setq auto-mode-alist (cons '("\\.ear$" . archive-mode) auto-mode-alist))
(setq auto-mode-alist (cons '("\\.rar$" . archive-mode) auto-mode-alist))
(setq auto-mode-alist (cons '("\\.sar$" . archive-mode) auto-mode-alist))
(setq auto-mode-alist (cons '("\\.aop$" . archive-mode) auto-mode-alist))
(setq auto-mode-alist (cons '("\\.esb$" . archive-mode) auto-mode-alist))


;;; Wanderlust
(autoload 'wl "wl" "Wanderlust" t)
(autoload 'wl-other-frame "wl" "Wanderlust on new frame." t)
(autoload 'wl-draft "wl-draft" "Write draft with Wanderlust." t)
(autoload 'wl-user-agent-compose "wl-draft" nil t)
(if (boundp 'mail-user-agent)
    (setq mail-user-agent 'wl-user-agent))
(if (fboundp 'define-mail-user-agent)
    (define-mail-user-agent
      'wl-user-agent
      'wl-user-agent-compose
      'wl-draft-send
      'wl-draft-kill
      'mail-send-hook))
(setq gnutls-min-prime-bits 1024)
(setq smtp-end-of-line "\n")
(setq wl-summary-weekday-name-lang "en")
(setq elmo-message-fetch-threshold 100000)
(require 'mime-w3m)


;; java
(define-derived-mode
  bsh-script-mode java-mode "bsh script"
  "Major mode for developing Beanshell scripts.")
(add-to-list 'auto-mode-alist '("\\.bsh\\'" . bsh-script-mode))
(defun javadoc ()
  (interactive)
  (w3m-goto-url "file:///usr/share/javadoc/java/allclasses-frame.html"))
(defun javadocse ()
  (interactive)
  (w3m-goto-url "http://docs.oracle.com/javase/8/docs/api/allclasses-frame.html"))
(defun javadocee ()
  (interactive)
  (w3m-goto-url "http://docs.oracle.com/javaee/7/api/allclasses-frame.html"))


;; C-c . jboss
(c-add-style "jboss" '("java"
                       (c-basic-offset . 3)
                       (c-offsets-alist (substatement-open . 0)
                                        (arglist-intro . +))))
;; C-c . 2tab
(c-add-style "2tab" '("java"
                     (c-basic-offset . 2)))


(setq ispell-program-name "aspell")

(global-set-key (kbd "C-z") nil)
(global-set-key (kbd "C-+") 'text-scale-increase)
(global-set-key (kbd "C--") 'text-scale-decrease)

;; Presentation notes
;; - Use C-c . 2tab
;; - Use hl-line-mode
;; - Move by block, C-M-a, C-M-e, C-M-h


;;; goto HOME
(cd "~/")
