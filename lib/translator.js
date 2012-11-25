
var k1 = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
var k2 = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ";
var k3 = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ";

var n1 = ' ';
var n2 = ' ';
var n3 = ' ';

function onkey(c, jamo)
{
    switch(jamo)
    {
        // 자음
        case 1:
            // 만약에 이전에 모음이 있었으면
            if(n2!=' ') 
            {        
                // 만약에 이전에 자음도 있었으면
                if(n3!=' ') 
                {
                    if(n3=='ㄱ' && c=='ㅅ')
                    {
                        n3='ㄳ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄴ' && c=='ㅎ')
                    {
                        n3='ㄶ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㄱ') 
                    {
                        n3='ㄺ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㅁ') 
                    {
                        n3='ㄻ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㅂ') 
                    {
                        n3='ㄼ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㅅ') 
                    {
                        n3='ㄽ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㅌ') 
                    {
                        n3='ㄾ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㅍ') 
                    {
                        n3='ㄿ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄹ' && c=='ㅎ') 
                    {
                        n3='ㅀ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㅂ' && c=='ㅅ')
                    {
                        n3='ㅄ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else 
                    {
                        n1 = c;
                        n2 = ' ';
                        n3 = ' ';
                        last = res;
                        res += n1;
                    }
                }
                // 이전에 모음만 있고 자음은 없었으면 아마 받침이 될 것임
                else
                {
                    // 이것들은 받침이 될 수 없음 
                    if(c=='ㄸ'||c=='ㅃ'||c=='ㅉ')
                    {
                        // 무조건 새 글자
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        n1 = c;
                        n2 = ' ';
                        n3 = ' ';
                        last = res;
                        res += n1;
                    }
                    // 무조건 받침으로 들어감
                    else 
                    {
                        n3=c;
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                }
            }
            // 이전에 모음이 없었던 경우이므로 무조건 출력함
            else
            {        
                n1=c;
                n2=' ';
                n3=' ';
                last = res;
                res += c;
            }
            break;
        // 모음
        case 2:
            // 만약에 이전에 모음이 있었으면
            if(n2!=' ') 
            {
                // 만약에 이전에 받침도 있었으면
                if(n3!=' ')
                {
                    if(n3=='ㄳ') 
                    {
                        n3='ㄱ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅅ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄵ') 
                    {
                        n3='ㄴ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅈ';
                        n2 = c;
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄶ') 
                    {
                        n3='ㄴ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅎ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄺ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㄱ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄻ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅁ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄼ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅂ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄽ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅅ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄾ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅌ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㄿ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅍ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㅀ') 
                    {
                        n3='ㄹ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅎ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    else if(n3=='ㅄ') 
                    {
                        n3='ㅂ';
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        // 새 글자
                        n1 = 'ㅅ';
                        n2 = c;
                        n3 = ' ';
                        last = res;
                        res += last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                    // 이전에 받침을 꾸어옴
                    else 
                    {
                        temp = n3;
                        n3 = ' ';
                        // 새 글자
                        res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                        n1 = temp;
                        n2 = c;
                        last = res;
                        res += String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                    }
                }
                else 
                {
                    if(n2=='ㅗ')
                    {
                        if(c=='ㅏ') n2='ㅘ';
                        if(c=='ㅐ') n2='ㅙ';
                        if(c=='ㅣ') n2='ㅚ';
                    }
                    if(n2=='ㅜ')
                    {
                        if(c=='ㅓ') n2='ㅝ';
                        if(c=='ㅔ') n2='ㅞ';
                        if(c=='ㅣ') n2='ㅟ';
                    }
                    if(n2=='ㅡ')
                    {
                        if(c=='ㅣ') n2='ㅢ';
                    }
                    res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                }
            }
            else 
            {
                // 만약에 이전에 자음이 있었으면 
                if(n1!=' ')
                {
                    n2=c;
                    res = last + String.fromCharCode(0xAC00 + k1.indexOf(n1)*21*28 + k2.indexOf(n2)*28 + k3.indexOf(n3));
                }
                else
                {
                    n1 = ' ';
                    n2 = ' ';
                    n3 = ' ';
                    last = res;
                    res += c;
                }
            }
            break;
        // 그 이외
        case 0:
            if(n1!=' '&&n2==' '&&n3==' ') 
            {
                last = res;
                res += n1;
            }
            else if(n1==' '&&n2!=' '&&n3==' ') 
            {
                last = res;
                res += n2;
            }
            n1 = ' ';
            n2 = ' ';
            n3 = ' ';
            last = res;
            res += c;
            break;
    }
}

function translate(etext)
{
	var ktext;
    res = "";
    last = "";
    n1 = ' ';
    n2 = ' ';
    n3 = ' ';
    eng = false;
    for(n=0; n<etext.length; n++)
    {
        jamo = 0;
        c = etext.charAt(n);
        if(c=='') 
        {
            eng = !eng;
            continue;
        }
        if(!eng)
        {
            switch(c)
            {
                // 키보드 제일 윗줄
                case 'q': c='ㅂ'; jamo=1; break;
                case 'Q': c='ㅃ'; jamo=1; break;
                case 'w': c='ㅈ'; jamo=1; break;
                case 'W': c='ㅉ'; jamo=1; break;
                case 'e': c='ㄷ'; jamo=1; break;
                case 'E': c='ㄸ'; jamo=1; break;
                case 'r': c='ㄱ'; jamo=1; break;
                case 'R': c='ㄲ'; jamo=1; break;
                case 't': c='ㅅ'; jamo=1; break;
                case 'T': c='ㅆ'; jamo=1; break;
                case 'y': c='ㅛ'; jamo=2; break;
                case 'u': c='ㅕ'; jamo=2; break;
                case 'i': c='ㅑ'; jamo=2; break;
                case 'o': c='ㅐ'; jamo=2; break;
                case 'O': c='ㅒ'; jamo=2; break;
                case 'p': c='ㅔ'; jamo=2; break;
                case 'P': c='ㅖ'; jamo=2; break;
            
                // 키보드 두번째 줄 
                case 'a': c='ㅁ'; jamo=1; break;
                case 's': c='ㄴ'; jamo=1; break;
                case 'd': c='ㅇ'; jamo=1; break;
                case 'f': c='ㄹ'; jamo=1; break;
                case 'g': c='ㅎ'; jamo=1; break;
                case 'h': c='ㅗ'; jamo=2; break;
                case 'j': c='ㅓ'; jamo=2; break;
                case 'k': c='ㅏ'; jamo=2; break;
                case 'l': c='ㅣ'; jamo=2; break;
            
                // 키보드 제일 밑 줄
                case 'z': c='ㅋ'; jamo=1; break;
                case 'x': c='ㅌ'; jamo=1; break;
                case 'c': c='ㅊ'; jamo=1; break;
                case 'v': c='ㅍ'; jamo=1; break;
                case 'b': c='ㅠ'; jamo=2; break;
                case 'n': c='ㅜ'; jamo=2; break;
                case 'm': c='ㅡ'; jamo=2; break;
            }
        }
        onkey(c,jamo)
    }
    ktext = res;
	return ktext;
}
