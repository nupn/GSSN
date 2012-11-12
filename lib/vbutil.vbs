	'한글코드 처리시에 사용한다.
	Public Function BinDecode(byVal binData)
        Dim i, byteChr, strV
        For i = 1 to LenB(binData)
            byteChr = AscB(MidB(binData,i,2))
            If byteChr > 127 Then
                i = i + 1
                strV = strV & Chr("&H" & Hex(byteChr) & Hex(AscB(MidB(binData,i,2))))
            Else
                strV = strV & Chr(byteChr)
            End if
        Next
        BinDecode = strV
    End Function


	Public Function URLEncodeUTF8(byVal szSource)

		Dim szChar, WideChar, nLength, i, result
		nLength = Len(szSource)

		'szSource = Replace(szSource," ","+")

		For i = 1 To nLength
		 szChar = Mid(szSource, i, 1)

		 If Asc(szChar) < 0 Then             
		  WideChar = CLng(AscB(MidB(szChar, 2, 1))) * 256 + AscB(MidB(szChar, 1, 1))

		  If (WideChar And &HFF80) = 0 Then
		   result = result & "%" & Hex(WideChar)
		  ElseIf (WideChar And &HF000) = 0 Then
		   result = result & _
		   "%" & Hex(CInt((WideChar And &HFFC0) / 64) Or &HC0) & _
		   "%" & Hex(WideChar And &H3F Or &H80)
		  Else
		   result = result & _
		   "%" & Hex(CInt((WideChar And &HF000) / 4096) Or &HE0) & _
		   "%" & Hex(CInt((WideChar And &HFFC0) / 64) And &H3F Or &H80) & _
		   "%" & Hex(WideChar And &H3F Or &H80)
		  End If
		 Else
		  result = result + szChar
		 End If
		Next
		URLEncodeUTF8 = result
	End Function


'---------------------------------------------------------------------
' URLDecodeUTF8 (UTF8 --> 아스키 )
' mongmong - 2003. 10 (URLEncodeUTF8 참조)
'---------------------------------------------------------------------


Public Function URLDecodeUTF8(byVal pURL)
	Dim i, s1, s2, s3, u1, u2, result
	pURL = Replace(pURL,"+"," ")

	For i = 1 to Len(pURL)

	 if Mid(pURL, i, 1) = "%" then

	  s1 = CLng("&H" & Mid(pURL, i + 1, 2))

	  '2바이트일 경우
	  if ((s1 AND &HC0) = &HC0) AND ((s1 AND &HE0) <> &HE0) then
	   s2 = CLng("&H" & Mid(pURL, i + 4, 2))

	   u1 = (s1 AND &H1C) / &H04
	   u2 = ((s1 AND &H03) * &H04 + ((s2 AND &H30) / &H10)) * &H10
	   u2 = u2 + (s2 AND &H0F)
	   result = result & ChrW((u1 * &H100) + u2)
	   i = i + 5

	  '3바이트일 경우
	  elseif (s1 AND &HE0 = &HE0) then
	   s2 = CLng("&H" & Mid(pURL, i + 4, 2))
	   s3 = CLng("&H" & Mid(pURL, i + 7, 2))

	   u1 = ((s1 AND &H0F) * &H10)
	   u1 = u1 + ((s2 AND &H3C) / &H04)
	   u2 = ((s2 AND &H03) * &H04 +  (s3 AND &H30) / &H10) * &H10
	   u2 = u2 + (s3 AND &H0F)
	   result = result & ChrW((u1 * &H100) + u2)
	   i = i + 8
	  end if
	 else
	  result = result & Mid(pURL, i, 1)
	 end if
	Next
	URLDecodeUTF8 = result
End Function
